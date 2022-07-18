package view;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import File.FileController;
import app.Main;
import app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * Controller for admin Interface
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class AdminController {
    @FXML
    private Button DeleteUserButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button NewUserAddbutton;

    @FXML
    private Button QuitButton;

    @FXML
    private TextField UserTextBox;
    
    ArrayList<User> users;
    @FXML
    ListView<User> userList;

    
    ObservableList<User> obsList;
    
    Stage stage;
    /**
     *
     * On log output press
     *
     * @param e
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    @FXML
    private void onLogOutPress(ActionEvent e) throws IOException, ClassNotFoundException {

        Stage stage = Main.getStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     *
     * Admin quit handler
     *
     * @param e
     */
    @FXML
    private void adminQuitHandler(ActionEvent e) {
        System.exit(1);
    }
    /**
     *
     * Add button handler
     *
     * @param e
     * @throws   IOException
     */
    @FXML
    private void addButtonHandler(ActionEvent e) throws IOException {
    	String userName = UserTextBox.getText().trim();
    	
    	if (userName.isEmpty()||userExists(userName)||userName.equals("Admin")) {
    		return;
    	}

        User user = new User(userName);
        obsList.add(user);
        users.add(user);

        UserTextBox.setText("");

        FileController.write(users);
    }
    /**
     *
     * Start
     *
     * @param stage
     * @throws   IOException
     * @throws  ClassNotFoundException
     */
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        try{
            users = FileController.read();
        }
        catch (Exception e){
            users=new ArrayList<User>();
            FileController.write(users);
        }


		obsList = FXCollections.observableArrayList();

		for(int i = 0; i < users.size(); i++) {
			obsList.add(users.get(i));
		}
		userList.setItems(obsList);
		
		userList.setCellFactory(param -> new ListCell<User>() {
			protected void updateItem(User item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null || item.getUserName() == null) {
					setText(null);
				}
				else setText(item.getUserName());
			}
		});

    }

    /**
     *
     * Delete button handler
     *
     * @param e
     * @throws   IOException
     */
    @FXML
    public void deleteButtonHandler(ActionEvent e) throws IOException {

        int rm=userList.getSelectionModel().getSelectedIndex();
        obsList.remove(rm);
        userList.setItems(obsList);
        users.remove(rm);

        FileController.write(users);

    }
    /**
     *
     * User exists
     *
     * @param userName
     * @return boolean
     */
    public boolean userExists(String userName){

        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserName().equals(userName)){
                return true;
            }
        }

        return false;
    }
}