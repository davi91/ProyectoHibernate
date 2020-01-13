package Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import clases.Estancia;
import clases.Estudiante;
import clases.Residencia;
import clases.Universidad;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import ui.EstanciasController;
import ui.ResidenciasController;
import utils.HQLManager;
import utils.InsertEstudianteDialog;
import utils.InsertUniversidadDialog;

public class App extends Application {

	public enum eWindowType {
		
		W_RESIDENCIAS,
		W_ESTANCIAS
	}
	
	private  ResidenciasController resiController;
	private  EstanciasController estanciaController;
	
	private Stage mainWindow;
	
	private ArrayList<Residencia> residencias;
	private ArrayList<Universidad> universidades;
	private ArrayList<Estancia> estancias;
	
	public ArrayList<Universidad> getUniversidades() {
		return universidades;
	}

	public void setUniversidades(ArrayList<Universidad> universidades) {
		this.universidades = universidades;
	}

	public ArrayList<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(ArrayList<Estancia> estancias) {
		this.estancias = estancias;
	}

	public ArrayList<Residencia> getResidencias() {
		return residencias;
	}

	public void setResidencias(ArrayList<Residencia> residencias) {
		this.residencias = residencias;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			
			// Cargamos los datos iniciales que se van a usar a lo largo de la App
			setResidencias(new ArrayList<Residencia>(HQLManager.getResidencias()));
			setUniversidades(new ArrayList<Universidad>(HQLManager.getUniversidades()));
			setEstancias(new ArrayList<Estancia>(HQLManager.getEstancias()));
					
			mainWindow = primaryStage;
			mainWindow.setTitle("Base Datos Residencias");
			
			launchWindow(eWindowType.W_RESIDENCIAS);
			
			mainWindow.show();
			
			
		} catch (Exception  e) {
			launchException(e);
		} catch( ExceptionInInitializerError ef ) {
			launchException(ef);
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
		
		Platform.exit();
	}
	
	public static void launchException(ExceptionInInitializerError e) {
		
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
		
		Platform.exit();
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
	
	public void insertTables() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Insercciones");
		alert.setHeaderText("¿Qué desea insertar?");
		
		ButtonType universidadBt = new ButtonType("Universidad");
		ButtonType estudiantesBt = new ButtonType("Estudiante");
		ButtonType cancelBt = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(universidadBt, estudiantesBt, cancelBt);
		alert.initOwner(mainWindow);
		Optional<ButtonType> selection = alert.showAndWait();
		
		if( selection.isPresent() ) {
			
			if( selection.get().equals(universidadBt) ) {
				
				InsertUniversidadDialog dialog = new InsertUniversidadDialog();
				dialog.initOwner(mainWindow);
				Optional<Universidad> universidad = dialog.showAndWait();

				if (universidad.isPresent()) {

					Alert infoAlert = new Alert(AlertType.INFORMATION);
					infoAlert.setTitle("Listo");
					infoAlert.initOwner(mainWindow);
					infoAlert.setContentText("Universidad insertada");

					infoAlert.showAndWait();

					// Añadimos la universidad a la lista
					getUniversidades().add(universidad.get());
				}
				
			} else if( selection.get().equals(estudiantesBt) ) {
				
				InsertEstudianteDialog dialog = new InsertEstudianteDialog(this);
				
				Optional<Estudiante> estudiante = dialog.showAndWait();
				
				if( estudiante.isPresent() ) {
					
					Alert infoAlert = new Alert(AlertType.INFORMATION);
					infoAlert.setTitle("Listo");
					infoAlert.initOwner(mainWindow);
					infoAlert.setContentText("Estudiante insertado");

					infoAlert.showAndWait();
				}
			}
		}
	}

}
