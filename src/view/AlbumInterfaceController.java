package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import File.FileController;
import app.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * The class Album interface controller
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class AlbumInterfaceController {

    @FXML
    private Button AddPhotoButton;

    @FXML
    private Button AddTagButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button CaptionButton;

    @FXML
    private Button CopyFromAlbumButton;

    @FXML
    private Button DeletePhotoButton;

    @FXML
    private Button DeleteTagButton;

    @FXML
    private Button DisplayButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button MoveFromAlbumButton;

    @FXML
    private Button QuitButton;

    @FXML
    private Button SlideshowButton;

    private User currentUser;

    @FXML
    private ListView<Photo> albumPhotoListView;

    ObservableList<Photo> obsList;

    private String tag1;
    private String tagValue1;

    private String tag2;
    private String tagValue2;

    ArrayList<User> users;

    private Album currentAlbum;

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public void helperDate(Date initialDate,Date finalDate){
        ObservableList<Photo> tempObsList=FXCollections.observableArrayList();
        for(int i=0;i<obsList.size();i++){
            Date curr=obsList.get(i).getDate();
            int comp=curr.compareTo(initialDate);
            int comp2=curr.compareTo(finalDate);
            if(comp>0&&comp2<0){
                tempObsList.add(obsList.get(i));
            }
        }
        obsList=tempObsList;
        albumPhotoListView.setItems(obsList);
        albumPhotoListView.refresh();
    }


    @FXML
    public void filterByDateHandler(ActionEvent e){
        DatePicker datePicker = new DatePicker();
        Dialog dialog = new Dialog();
        dialog.setTitle("initial Date");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContent(datePicker);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Optional<Date>ret=dialog.showAndWait();


        Date dateStart=convertToDateViaInstant(datePicker.getValue());

        DatePicker datePicker2 = new DatePicker();
        Dialog dialog2 = new Dialog();
        dialog2.setTitle("final Date");
        dialog2.setHeaderText("Enter");
        dialog2.getDialogPane().setContent(datePicker2);
        dialog2.getDialogPane().getButtonTypes().add(ButtonType.OK);
        ret=dialog.showAndWait();

        Date dateFinal=convertToDateViaInstant(datePicker.getValue());


        helperDate(dateStart,dateFinal);


    }

    /**
     *
     * Display button handler
     * Displays the selcted photo with tags,date and caption
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    public void displayButtonHandler(ActionEvent e) throws IOException, ClassNotFoundException {
        Stage stage = Main.getStage();
        Photo temp3 = albumPhotoListView.getSelectionModel().getSelectedItem();
        int temp2=0;
        for(int i=0;i<currentAlbum.getPhotoList().size();i++){
            if(currentAlbum.getPhotoList().get(i).getPath().equals(temp3.getPath())){
                temp2=i;
            }
        }
        Photo temp = currentAlbum.getPhotoList().get(temp2);

        if (temp == null) {
            FileController.showAlert("No Selection", "Select Photo", "Cannot display nothing");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayScreen.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        DisplayScreenController cont1 = loader.getController();
        cont1.start(stage, currentUser, currentAlbum, currentAlbum.getPhotoList().get(temp2));

        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     *
     * Slideshow button handler
     * Takes to slide show interface
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    public void slideshowButtonHandler(ActionEvent e) throws IOException, ClassNotFoundException {

        if (currentAlbum.getPhotoList().size() == 0) {
            return;
        }

        Stage stage = Main.getStage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SlideShow.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        SlideShowController cont1 = loader.getController();

        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null)
            cont1.start(stage, currentUser, currentAlbum, 0);
        else
            cont1.start(stage, currentUser, currentAlbum, albumPhotoListView.getSelectionModel().getSelectedIndex());

        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     *
     * Back button handler
     * Takes back to list of albums for the current user
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    public void backButtonHandler(ActionEvent e) throws IOException, ClassNotFoundException {
        Stage stage = Main.getStage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInterface.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        UserInterfaceController cont1 = loader.getController();
        cont1.start(stage, currentUser);
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     *
     * Quit button handler
     *
     * @param e
     */
    @FXML
    public void QuitButtonHandler(ActionEvent e) {
        System.exit(1);
    }
    /**
     *
     * Log output button handler
     *
     * @param e
     * @throws   IOException
     */
    @FXML
    public void LogOutButtonHandler(ActionEvent e) throws IOException {
        Stage stage = Main.getStage();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root = loader.load(getClass().getResource("UserLogin.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     *
     * Clear filter button handler
     * Clears all the filter takes back to the album home screen
     * @param e
     */
    @FXML
    public void clearFilterButtonHandler(ActionEvent e) {
        tag1 = null;
        obsList = FXCollections.observableArrayList();
        for (int i = 0; i < currentAlbum.getPhotoList().size(); i++) {
            obsList.add(currentAlbum.getPhotoList().get(i));
        }

        albumPhotoListView.refresh();
        albumPhotoListView.setItems(obsList);
    }
    /**
     *
     * Tag one button handler
     *
     * @param e
     */
    @FXML
    public void tagOneButtonHandler(ActionEvent e) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter Tag one");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Tag?");
        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tag1 = result.get();
        }
        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setTitle("Filter Tag one");
        dialog2.setHeaderText("Enter");
        dialog2.getDialogPane().setContentText("Value?");
        result = dialog2.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tagValue1 = result.get();
        }
        ObservableList<Photo> tempObs=FXCollections.observableArrayList();
        for (int i = 0; i < obsList.size(); i++) {
            //per photo
            boolean z = false;
            ArrayList<Tag> temp = obsList.get(i).getTags();
            //all tags
            for (int j = 0; j < temp.size(); j++) {
                String tg = temp.get(j).getTag();
                String tval=temp.get(j).getTagValue();
                if (tg.equals(tag1)&&tval.equals(tagValue1)) {
                
                    z = true;
                }
            }
            if (z) {
                tempObs.add(obsList.get(i));
            }
            String t=obsList.get(i).getPath();
        }
        obsList=tempObs;
        albumPhotoListView.setItems(obsList);
        albumPhotoListView.refresh();
        tagValue1=null;
        tag1 = null;
    }
    /**
     *
     * Filter between tag 1 and tag 2
     *
     * @param e
     */
    @FXML
    public void tagAndHandler(ActionEvent e) {
        ObservableList<Photo> tempObs=FXCollections.observableArrayList();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("And Filter");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Tag1?");
        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tag1 = result.get();
        }
        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setTitle("And Filter");
        dialog2.setHeaderText("Enter");
        dialog2.getDialogPane().setContentText("Value?");
        result = dialog2.showAndWait();
        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tagValue1 = result.get();
        }
        
        TextInputDialog dialog3 = new TextInputDialog();
        dialog3.setTitle("And Filter");
        dialog3.setHeaderText("Enter");
        dialog3.getDialogPane().setContentText("Tag2?");
        result = dialog3.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tag2 = result.get();
        }
        TextInputDialog dialog4 = new TextInputDialog();
        dialog4.setTitle("And Filter");
        dialog4.setHeaderText("Enter");
        dialog4.getDialogPane().setContentText("Value?");
        result = dialog4.showAndWait();
        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tagValue2 = result.get();
        }
        


        for (int i = 0; i < obsList.size(); i++) {
            boolean z = false;
            boolean y = false;
            ArrayList<Tag> temp = obsList.get(i).getTags();
            for (int j = 0; j < temp.size(); j++) {
                String tg=temp.get(j).getTag();
                String tVal = temp.get(j).getTagValue();
                if (tg.equals(tag1)&&tVal.equals(tagValue1)) {
                    y = true;
                }
                if (tg.equals(tag2)&&tVal.equals(tagValue2)) {
                    z = true;
                }
            }
            if (z && y) {
                tempObs.add(obsList.get(i));
            }
        }
        obsList=tempObs;
        albumPhotoListView.setItems(obsList);
        albumPhotoListView.refresh();

        tag1 = null;
        tagValue1=null;
        tag2 = null;
        tagValue2=null;
    }
    /**
     *
     * Filter between tag 1 or tag 2
     *
     * @param e
     */
    @FXML
    public void tagOrHandler(ActionEvent e) {
        ObservableList<Photo> tempObs=FXCollections.observableArrayList();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("And Filter");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Tag1?");
        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tag1 = result.get();
        }
        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setTitle("And Filter");
        dialog2.setHeaderText("Enter");
        dialog2.getDialogPane().setContentText("Value?");
        result = dialog2.showAndWait();
        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tagValue1 = result.get();
        }

        TextInputDialog dialog3 = new TextInputDialog();
        dialog3.setTitle("And Filter");
        dialog3.setHeaderText("Enter");
        dialog3.getDialogPane().setContentText("Tag2?");
        result = dialog3.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tag2 = result.get();
        }
        TextInputDialog dialog4 = new TextInputDialog();
        dialog4.setTitle("And Filter");
        dialog4.setHeaderText("Enter");
        dialog4.getDialogPane().setContentText("Value?");
        result = dialog4.showAndWait();
        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Tag Empty", "no tags selected", "Please add a tag");
            return;
        } else {
            tagValue2 = result.get();
        }



        for (int i = 0; i < obsList.size(); i++) {
            boolean z = false;
            boolean y = false;
            ArrayList<Tag> temp = obsList.get(i).getTags();
            for (int j = 0; j < temp.size(); j++) {
                String tg=temp.get(j).getTag();
                String tVal = temp.get(j).getTagValue();
                if (tg.equals(tag1)&&tVal.equals(tagValue1)) {
                    y = true;
                }
                if (tg.equals(tag2)&&tVal.equals(tagValue2)) {
                    z = true;
                }
            }
            if (z || y) {
                tempObs.add(obsList.get(i));
            }
        }
        obsList=tempObs;
        albumPhotoListView.setItems(obsList);
        albumPhotoListView.refresh();

        tag1 = null;
        tagValue1=null;
        tag2 = null;
        tagValue2=null;
    }
    /**
     *
     * Start
     *
     * @param stage
     * @param currentUser
     * @param Album
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    public void start(Stage stage, User currentUser, String Album) throws IOException, ClassNotFoundException {
//        albumPhotoListView = new ListView<>();


        users = FileController.read();
        this.currentUser = currentUser;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(currentUser.getUserName())) {
                ArrayList<Album> uAlbums = users.get(i).getAlbumList();
                for (int j = 0; j < uAlbums.size(); j++) {
                    if (uAlbums.get(j).getAlbumname().equals(Album)) {
                        currentAlbum = uAlbums.get(j);
                    }
                }
            }
        }

        obsList = FXCollections.observableArrayList();


        for (int i = 0; i < currentAlbum.getPhotoList().size(); i++) {
            obsList.add(currentAlbum.getPhotoList().get(i));
        }

        albumPhotoListView.refresh();
        albumPhotoListView.setItems(obsList);
        albumPhotoListView.setCellFactory(param -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Photo item, boolean empty) {

                super.updateItem(item, empty);

                try {
                    if (empty || item == null || item.getImage() == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        imageView.setImage(item.getImage());
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(120);
                        setGraphic(imageView);
                        setText(item.getCaption()+"          "+item.getDate());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    /**
     *
     * Add button handler
     * Add photo to the album
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    public void AddButtonHandler(ActionEvent e) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage stage = Main.getStage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Photo tempPhoto = new Photo();
            tempPhoto.setImage(file.getPath());

            for(int i=0;i<obsList.size();i++){
                if(obsList.get(i).getPath().equals(tempPhoto.getPath())){
                    FileController.showAlert("Duplicate Photo","Add different photos","No duplicates L");
                    return;
                }
            }

            currentAlbum.getPhotoList().add(tempPhoto);
            obsList.add(tempPhoto);
            albumPhotoListView.setItems(obsList);
            FileController.write(users);
        }

    }

    /**
     *
     * Delete button handler
     * Deletes photo from the album
     * @param e
     * @throws   IOException
     */
    @FXML
    public void DeleteButtonHandler(ActionEvent e) throws IOException {
        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int rm = albumPhotoListView.getSelectionModel().getSelectedIndex();
        obsList.remove(rm);
        albumPhotoListView.setItems(obsList);

        currentAlbum.getPhotoList().remove(rm);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(currentUser.getUserName())) {
                ArrayList<Album> uAlbums = users.get(i).getAlbumList();
                for (int j = 0; j < uAlbums.size(); j++) {
                    if (uAlbums.get(j).getAlbumname().equals(currentAlbum.getAlbumname())) {
                        uAlbums.get(i).setPhotoList(currentAlbum.getPhotoList());
                    }
                }
            }
        }
        FileController.write(users);
    }
    /**
     *
     * Create filtered album button handler
     * Creates album on filtered applied photos
     * @param e
     * @throws   IOException
     */
    @FXML
    public void createFilteredAlbumButtonHandler(ActionEvent e) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filtered Album");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Album Name");
        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Album Empty", "Enter Name", "Please add a valid Album name");
            return;
        } else if (AlbumsExist(result.get())) {
            FileController.showAlert("Album Exists", "Enter Valid Album Name", "Please add a valid Album name");
            return;
        }

        Album temp = new Album(result.get());

        for (int i = 0; i < obsList.size(); i++) {
            temp.addPhotoToAlbum(obsList.get(i));
        }
        currentUser.addAlbumToUser(temp);

        this.currentUser = currentUser;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(currentUser.getUserName())) {
                users.get(i).addAlbumToUser(temp);
            }
        }
        FileController.write(users);
    }

    /**
     *
     * Albums exist
     * Checks if album exhist
     * @param albumbox
     * @return boolean
     */
    private boolean AlbumsExist(String albumbox) {
        for (int i = 0; i < currentUser.getAlbumList().size(); i++) {
            if (currentUser.getAlbumList().get(i).getAlbumname().equals(albumbox)) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * Add tag handler
     * Adds tag to the photo
     * @param e
     * @throws   IOException
     */
    @FXML
    public void addTagHandler(ActionEvent e) throws IOException {

        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int rm = albumPhotoListView.getSelectionModel().getSelectedIndex();
        Photo temp = obsList.get(rm);


        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Tag");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Tag?");
        Optional<String> result = dialog.showAndWait();

        String tagA;
        if (!result.isPresent()) {
            return;
        } else {
            tagA = result.get();
        }

        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setTitle("Add Value");
        dialog2.setHeaderText("Enter");
        dialog2.getDialogPane().setContentText("Value?");
        result = dialog2.showAndWait();

        String tagB;
        if (!result.isPresent()) {
            return;
        } else {
            tagB = result.get();
        }


        temp.addTagList(tagA,tagB);
        obsList.set(rm, temp);
        albumPhotoListView.setItems(obsList);

        currentAlbum.getPhotoList().set(rm, temp);
        FileController.write(users);
    }
    /**
     *
     * Add tag handler
     * Deletes tag of the photo
     * @param e
     * @throws   IOException
     */
    @FXML
    public void deleteTagHandler(ActionEvent e) throws IOException {
        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int rm = albumPhotoListView.getSelectionModel().getSelectedIndex();
        Photo temp = obsList.get(rm);

       


        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Del Tag");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Tag?");
        Optional<String> result = dialog.showAndWait();

        String tagA;
        if (!result.isPresent()) {
            return;
        } else {
            tagA = result.get();
        }


        TextInputDialog dialog2 = new TextInputDialog();
        dialog2.setTitle("Del Value");
        dialog2.setHeaderText("Enter");
        dialog2.getDialogPane().setContentText("Value?");
        result = dialog2.showAndWait();

        String tagB;
        if (!result.isPresent()) {
            return;
        } else {
            tagB = result.get();
        }


        temp.removeTagList(tagA,tagB);
        obsList.set(rm, temp);
        albumPhotoListView.setItems(obsList);
        currentAlbum.getPhotoList().set(rm, temp);
        FileController.write(users);
    }
    /**
     *
     * Caption Button handler
     * caption or recaption photo
     * @param e
     * @throws   IOException
     */
    @FXML
    public void captionButtonHandler(ActionEvent e) throws IOException {


        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int rm = albumPhotoListView.getSelectionModel().getSelectedIndex();
        Photo temp = obsList.get(rm);

        
        Label displayLabel = new Label();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set Caption");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("CAPTION");
        Optional<String> result = dialog.showAndWait();

        String capt;
        if (!result.isPresent()) {
            capt = "";
        } else {
            capt = result.get();
        }

        temp.setCaption(capt);
        obsList.set(rm, temp);
        albumPhotoListView.setItems(obsList);

        currentAlbum.getPhotoList().get(rm).setCaption(capt);
//        for(int i =0; i< users.size(); i++){
//            if(users.get(i).getUserName().equals(currentUser.getUserName())){
//                ArrayList<Album> uAlbums = users.get(i).getAlbumList();
//                for(int j = 0; j< uAlbums.size(); j++){
//                    if(uAlbums.get(j).getAlbumname().equals(currentAlbum.getAlbumname())){
//                        uAlbums.get(i).setPhotoList(currentAlbum.getPhotoList());
//                    }
//                }
//            }
//        }
        FileController.write(users);

    }
    /**
     *
     * Move photo handler
     * Moves photo to another album
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    public void movePhotoHandler(ActionEvent e) throws IOException, ClassNotFoundException {
        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int albIndex = albumPhotoListView.getSelectionModel().getSelectedIndex();
        Photo temp = currentAlbum.getPhotoList().get(albIndex);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Move Photo");
        dialog.setHeaderText("Enter");
        dialog.getDialogPane().setContentText("Reciever Album Name");
        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Album Empty", "Enter Name", "Please add a valid Album name");
            return;

        } else if(result.get().equals(currentAlbum.getAlbumname())){
            FileController.showAlert("Cannot Move", "Cannot Move to itself", "Please add a valid Album name other than current");
            return;
        }
        else if (AlbumsExist(result.get())) {
            FileController.showAlert("Album Doesnt Exists", "Enter Valid Album Name", "Please add a valid Album name");
            return;
        }
        String recieveAlbum = result.get();


        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(currentUser.getUserName())) {
                for (int j = 0; j < users.get(i).getAlbumList().size(); j++) {
                    if (users.get(i).getAlbumList().get(j).getAlbumname().equals(recieveAlbum)) {
                        for(int x=0;i<users.get(i).getAlbumList().get(j).getPhotoList().size();x++){
                            if(users.get(i).getAlbumList().get(j).getPhotoList().get(x).getPath().equals(temp.getPath())){
                                FileController.showAlert("Duplicate Photo","Add different photos","No duplicates L");
                                return;
                            }
                        }
                        users.get(i).getAlbumList().get(j).addPhotoToAlbum(temp);
                    }
                    if (users.get(i).getAlbumList().get(j).getAlbumname().equals(currentAlbum.getAlbumname())) {
                        users.get(i).getAlbumList().get(j).getPhotoList().remove(albIndex);
                    }
                }
            }
        }
        obsList.remove(albIndex);
        FileController.write(users);
    }
    /**
     *
     * Copy photo handler
     * Copies photo from another album to this album
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    public void copyPhotoHandler(ActionEvent e) throws IOException, ClassNotFoundException {
        if (albumPhotoListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int albIndex = albumPhotoListView.getSelectionModel().getSelectedIndex();
        Photo temp = currentAlbum.getPhotoList().get(albIndex);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Copy Photo");
        dialog.getDialogPane().setContentText("Reciever Album Name");
        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            return;
        } else if (result.get().equals("")) {
            FileController.showAlert("Album Empty", "Enter Name", "Please add a valid Album name");
            return;
        } 
        else if (AlbumsExist(result.get())) {
            FileController.showAlert("Album Doesnt Exists", "Enter Valid Album Name", "Please add a valid Album name");
            return;
        }
        String recieveAlbum = result.get();

        users = FileController.read();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(currentUser.getUserName())) {
                for (int j = 0; j < users.get(i).getAlbumList().size(); j++) {
                    if (users.get(i).getAlbumList().get(j).getAlbumname().equals(recieveAlbum)) {
                        for(int x=0;i<users.get(i).getAlbumList().get(j).getPhotoList().size();x++){
                            if(users.get(i).getAlbumList().get(j).getPhotoList().get(x).getPath().equals(temp.getPath())){
                                FileController.showAlert("Duplicate Photo","Add different photos","No duplicates L");
                                return;
                            }
                        }
                        users.get(i).getAlbumList().get(j).addPhotoToAlbum(temp);
                    }
                }
            }
        }
        FileController.write(users);


    }

}
