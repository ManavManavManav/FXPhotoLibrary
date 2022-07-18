package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import File.FileController;
import app.Album;
import app.Main;
import app.Photo;
import app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The class Login controller
 * @author Manankumar Patel mpp128
 * @author Manav Gohil mrg225
 */
public class LoginController {

	@FXML
	private TextField userInputBox;

	@FXML
	private Button LoginButton;

	@FXML
	private Button LoginPageQuitButton;
	/**
	 *
	 * On login press
	 *Logs in if the user exist, shows error if user is not created by the admin
	 * @param e
	 * @throws   IOException
	 * @throws  ClassNotFoundException
	 * @throws  InterruptedException
	 */
	@FXML
	private void onLoginPress(ActionEvent e) throws IOException, ClassNotFoundException, InterruptedException {
		String username = userInputBox.getText();
		Path temp=Paths.get("src/data/Users.ser");
		File f=new File(String.valueOf(temp));
		if (username.equalsIgnoreCase("Admin")) {
			Stage stage = Main.getStage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminInterface.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			AdminController cont1 = loader.getController();
			cont1.start(stage);
			stage.setScene(new Scene(root));
			stage.show();
		}
		else if(!f.exists()){
			FileController.showAlert("No Users","No users present","Admin has to add new users");
		} else if(UserExists(username)){
			Stage stage = Main.getStage();
			User currentUser = FindUser(username);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInterface.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			UserInterfaceController cont1 = loader.getController();
			cont1.start(stage, currentUser);
			stage.setScene(new Scene(root));
			stage.show();
		}
		else{
			FileController.showAlert("User Not Found","Invalid username","You can only login if user exists");
			return;
		}
	}
	/**
	 *
	 * Handle on action quit
	 * Quits the application
	 * @param e
	 */
	@FXML
	private void handleOnActionQuit(ActionEvent e) {
		System.exit(1);
	}

	/**
	 *
	 * User exists
	 * Checks if the user exist
	 * @param userName
	 * @return boolean
	 * @throws   IOException
	 * @throws  ClassNotFoundException
	 */
	public boolean UserExists(String userName) throws IOException, ClassNotFoundException {
		ArrayList<User> users = FileController.read();

		for(int i=0;i<users.size();i++){
			if(users.get(i).getUserName().equals(userName)){
				return true;
			}
		}
		return false;
	}
	/**
	 *
	 * Find user
	 *
	 * @param userName
	 * @return User
	 * @throws   IOException
	 * @throws  ClassNotFoundException
	 */
	public User FindUser(String userName) throws IOException, ClassNotFoundException {
		ArrayList<User> users = FileController.read();

		for(int i=0;i<users.size();i++){
			if(users.get(i).getUserName().equals(userName)){
				return users.get(i);
			}
		}

		return null;
	}

	public void stockPhotoScam() throws IOException, ClassNotFoundException {
		Path teemp=Paths.get("src/data/Users.ser");
		File f=new File(String.valueOf(teemp));
		if(f.exists()){
			return;
		}
		Photo one = new Photo();
		Photo two = new Photo();
		Photo three = new Photo();
		Photo four = new Photo();
		Photo five = new Photo();

		Path onePath = Paths.get("src/heap/healthyfood");
		Path twoPath = Paths.get("src/heap/mcqueen");
		Path threePath = Paths.get("src/heap/racoon");
		Path fourPath = Paths.get("src/heap/spongebob");
		Path fivePath = Paths.get("src/heap/cat.jpg");

		one.setImage(onePath.toString());
		two.setImage(twoPath.toString());
		three.setImage(threePath.toString());
		four.setImage(fourPath.toString());
		five.setImage(fivePath.toString());

		Album temp = new Album("Stock");
		temp.getPhotoList().add(one);
		temp.getPhotoList().add(two);
		temp.getPhotoList().add(three);
		temp.getPhotoList().add(four);
		temp.getPhotoList().add(five);

		User stock = new User("stock");
		stock.getAlbumList().add(temp);



		ArrayList<User> users;
		try {
			users = FileController.read();
		} catch (Exception e) {
			users = new ArrayList<User>();
			FileController.write(users);
		}
			users.add(stock);


		FileController.write(users);


	}



}
