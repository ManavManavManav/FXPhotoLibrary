package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import view.LoginController;
/**
 * The class Main extends application
 *  @author Manankumar Patel mpp128
 *  @author Manav Gohil mrg225
 */
public class Main extends Application {
	
	public static Stage primaryStage;
	/**
	 *
	 * Gets the stage
	 *
	 * @return the stage
	 */
	public static Stage getStage() {
		return primaryStage;
	}
	/**
	 *
	 * Sets the stage
	 *
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.primaryStage=stage;
	}

	/**
	 *
	 * Start
	 *
	 * @param stage
	 * @throws   Exception
	 */
	@Override
	public void start(Stage stage) throws Exception {
		setStage(stage);
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/UserLogin.fxml"));
		AnchorPane root=(AnchorPane)loader.load();

		LoginController cont1=loader.getController();
		cont1.stockPhotoScam();

		Scene scene = new Scene(root);
		stage.setTitle("Welcome User");
		stage.setScene(scene);
		stage.show();
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}


}