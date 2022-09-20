package app;
//Roma Patel - rkp86
//Omer Farooq - ofs9
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;


public class SongLib extends Application {

	Stage mainstage;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// set up FXML loader
		mainstage = stage;
		mainstage.setTitle("Song Library");
		
		try {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/scene.fxml"));
		
		// load the fxml
		AnchorPane root = (AnchorPane)loader.load();

		// get the controller (Do NOT create a new Controller()!!)
		// instead, get it through the loader
		
		Controller controller = loader.getController();
		controller.start(stage);

		Scene scene = new Scene(root); 
		stage.setScene(scene);
		stage.show(); 
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
