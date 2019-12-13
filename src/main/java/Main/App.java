package Main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ResidenciasController;

public class App extends Application {

	private ResidenciasController resiController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			resiController = new ResidenciasController();
			
			primaryStage.setTitle("Base Datos Residencias");
			primaryStage.setScene(new Scene(resiController.getRootView(), 640, 480));
			primaryStage.show();
			
		} catch (IOException e) {
		}
		

	}

	public static void main(String[] args) {
		launch(args);

	}

}
