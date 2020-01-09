package ui;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Main.App;
import Main.App.eWindowType;
import clases.Estancia;
import clases.Estudiante;
import clases.Residencia;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import utils.HQLManager;
import utils.InsertEstanciaDialog;

public class EstanciasController implements Initializable {

	// FXML : View
	//-------------------------------------------------
	
	@FXML
	private GridPane view;
	
	@FXML
	private TableView<Residencia> resiTable;

	@FXML
	private TableView<Estudiante> estuTable;

	@FXML
	private TableView<Estancia> estanciaTable;

	@FXML
	private Button resiBt;

	@FXML
	private Button insertBt;

	@FXML
	private Button insertEstBt;

	@FXML
	private Button modEstBt;

	@FXML
	private Button removeEstBt;
	
	//-------------------------------------------------
	
	// Model
	private ListProperty<Estancia> estanciasList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	
	
	// Necesitamos una referencia a la aplicación principal
	private App myApp;
	
	public EstanciasController(App myApp) throws IOException {
		
		this.myApp = myApp;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EstanciasView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		estanciasList.setAll(App.getEstancias());
		
		estanciaTable.itemsProperty().bind(estanciasList);
		
		insertEstBt.disableProperty().bind(estanciaTable.getSelectionModel().selectedItemProperty().isNull());
		modEstBt.disableProperty().bind(estanciaTable.getSelectionModel().selectedItemProperty().isNull());
		removeEstBt.disableProperty().bind(estanciaTable.getSelectionModel().selectedItemProperty().isNull());

	}
	
    @FXML
    void goResidencia(ActionEvent event) {

		// Cambiamos de situación, ahora mostramos las residencias
		try {
			myApp.launchWindow(eWindowType.W_RESIDENCIAS);
		} catch (IOException e) {
			App.launchException(e);
		}
    }

    @FXML
    void insertEstancia(ActionEvent event) {

    	InsertEstanciaDialog dialog = new InsertEstanciaDialog();
    	
    	Optional<Estancia> estancia = dialog.showAndWait();
    	
    	if( estancia.isPresent() && estancia.get() != null ) {
    		
    		estanciasList.add(estancia.get());
    		myApp.getEstancias().add(estancia.get());
    		
    		Alert infoAlert = new Alert(AlertType.INFORMATION);
    		infoAlert.setTitle("Éxito");
    		infoAlert.setHeaderText("Estancia insertada con éxito");
    		infoAlert.showAndWait();
    	}
    }

    @FXML
    void insertTables(ActionEvent event) {
    	myApp.insertTables();
    }

    @FXML
    void removeEstancia(ActionEvent event) {

		ButtonType yesBt = new ButtonType("Si", ButtonData.OK_DONE);
		ButtonType noBt = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getDialogPane().getButtonTypes().clear();
		alert.getDialogPane().getButtonTypes().addAll(yesBt, noBt);
		alert.initOwner(getRootView().getScene().getWindow());
		
		alert.setTitle("Confirmacion");
		alert.setHeaderText("Eliminar estancia");
		alert.setContentText("Se eliminarán los estudiantes asociados");
		alert.setContentText("¿Está seguro de eliminar esta estancia?");
		
		Optional<ButtonType> response = alert.showAndWait();
		
		if( response.isPresent() && response.get() == yesBt) {
			
			Estancia estancia = estanciaTable.getSelectionModel().getSelectedItem();
			
			if( estancia != null ) {
				HQLManager.eliminarEstancia(estancia);
			}
			HQLManager.eliminarEstancia(estancia);
		}
    }

    @FXML
    void updateEstancia(ActionEvent event) {

    	InsertEstanciaDialog dialog = new InsertEstanciaDialog(estanciaTable.getSelectionModel().getSelectedItem());
    	
    	Optional<Estancia> estancia = dialog.showAndWait();
    	
    	if( estancia.isPresent() ) {
    		Alert infoAlert = new Alert(AlertType.INFORMATION);
    		infoAlert.setTitle("Éxito");
    		infoAlert.setHeaderText("Estancia actualizada con éxito");
    		infoAlert.showAndWait();
    	}
    	
    }
    
    public GridPane getRootView() {
		return view;
	}
}
