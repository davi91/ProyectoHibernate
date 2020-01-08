package Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import clases.Estancia;
import clases.Residencia;
import clases.Universidad;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ui.EstanciasController;
import ui.ResidenciasController;
import utils.HQLManager;

public class App extends Application {

	public enum eWindowType {
		
		W_RESIDENCIAS,
		W_ESTANCIAS
	}
	
	private  ResidenciasController resiController;
	private  EstanciasController estanciaController;
	
	private Stage mainWindow;
	
	private static ArrayList<Residencia> residencias;
	private static ArrayList<Universidad> universidades;
	private static ArrayList<Estancia> estancias;
	
	public static ArrayList<Universidad> getUniversidades() {
		return universidades;
	}

	public static void setUniversidades(ArrayList<Universidad> universidades) {
		App.universidades = universidades;
	}

	public static ArrayList<Estancia> getEstancias() {
		return estancias;
	}

	public static void setEstancias(ArrayList<Estancia> estancias) {
		App.estancias = estancias;
	}

	public static ArrayList<Residencia> getResidencias() {
		return residencias;
	}

	public static void setResidencias(ArrayList<Residencia> residencias) {
		App.residencias = residencias;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Cargamos los datos iniciales que se van a usar a lo largo de la App
		setResidencias(new ArrayList<Residencia>(HQLManager.getResidencias()));
		setUniversidades(new ArrayList<Universidad>(HQLManager.getUniversidades()));
		setEstancias(new ArrayList<Estancia>(HQLManager.getEstancias()));
		
		try {
			
			mainWindow = primaryStage;
			launchWindow(eWindowType.W_RESIDENCIAS);
			mainWindow.setTitle("Base Datos Residencias");
			mainWindow.show();
			
		} catch (Exception e) {
			launchException(e);
		}
		

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void launchException(Exception e) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Excepción");
		alert.setHeaderText("Error fatal");

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("Contenido del error:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
	
	public void launchWindow(eWindowType type) throws IOException {
		
		
		switch (type) {

		case W_RESIDENCIAS:
			resiController = new ResidenciasController(this);
			Scene sceneResidencias = new Scene(resiController.getRootView(), 640, 480);
			mainWindow.setScene(sceneResidencias);
			break;

		case W_ESTANCIAS:
			estanciaController = new EstanciasController(this);
			Scene sceneEstancias = new Scene(estanciaController.getRootView(), 640, 480);
			mainWindow.setScene(sceneEstancias);
			break;

		default:
			break;
		}
		
	}

}
