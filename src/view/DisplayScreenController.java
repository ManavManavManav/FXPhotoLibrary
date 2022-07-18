package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import app.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



/**
 * The class Display screen controller
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class DisplayScreenController {

	@FXML
	private Button BackButton;

	@FXML
	private BorderPane DisplayScreenBorderPane;

	@FXML
	private TextField DisplayScreenCaption;

	@FXML
	private TextField DisplayScreenDate;

	@FXML
	private Button DisplayScreenQuit;

	@FXML
	private TextField DisplayScreenTag;

	@FXML
	private Button LogOutButton;

	@FXML
	private ListView tagsListView;

	@FXML
	private ImageView displayImageView;

	private Album currentAlbum;
	private Photo currentPhoto;
	private User currentUser;
	/**
	 *
	 * Back button handler
	 * Takes you back to AlbumInterface
	 * @param e
	 * @throws   IOException
	 * @throws  ClassNotFoundException
	 */
	@FXML
    public void backButtonHandler(ActionEvent e) throws IOException, ClassNotFoundException {
    	Stage stage=Main.getStage();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("AlbumInterface.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		AlbumInterfaceController cont1 = loader.getController();
		cont1.start(stage, currentUser, currentAlbum.getAlbumname());
		stage.setScene(new Scene(root));
		stage.show();
    }
	/**
	 *
	 * Quit button handler
	 * Quits the application
	 * @param e
	 */
    @FXML
    public void QuitButtonHandler(ActionEvent e) {
    	System.exit(1);
    }
	/**
	 *
	 * Log output button handler
	 * Logouts the current user
	 * @param e  the e
	 * @throws   IOException
	 */
    @FXML
    public void LogOutButtonHandler(ActionEvent e) throws IOException {
    	Stage stage=Main.getStage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root=loader.load(getClass().getResource("UserLogin.fxml"));
		stage.setScene(new Scene(root));
		stage.show();
    }
	/**
	 *
	 * Start
	 *
	 * @param stage
	 * @param currentUser
	 * @param currentAlbum
	 * @param currentPhoto
	 * @throws   FileNotFoundException
	 */
	public void start(Stage stage, User currentUser, Album currentAlbum, Photo currentPhoto) throws FileNotFoundException {
		this.currentUser=currentUser;
		this.currentPhoto=currentPhoto;
		this.currentAlbum=currentAlbum;
		displayImageView.setImage(currentPhoto.getImage());
		DisplayScreenCaption.setText(currentPhoto.getCaption());
		DisplayScreenDate.setText(String.valueOf(currentPhoto.getDate()));


		if(currentPhoto.getTags()!=null){
			ObservableList<Tag> obsList= FXCollections.observableArrayList();
			for(int i=0;i<currentPhoto.getTags().size();i++){
				obsList.add(currentPhoto.getTags().get(i));
			}
			tagsListView.setItems(obsList);
			tagsListView.setCellFactory(param -> new ListCell<Tag>() {
				protected void updateItem(Tag item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null || item.getTag() == null) {
						setText(null);
					}
					else setText(item.getTag()+":"+item.getTagValue());
				}
			});
		}

	}
	

}
