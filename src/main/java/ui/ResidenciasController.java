package ui;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Main.App;
import Main.App.eWindowType;
import Main.HibernateUtil;
import clases.Estudiante;
import clases.Residencia;
import clases.ResidenciasObservacion;
import clases.Universidad;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.GridPane;
import utils.HQLManager;
import utils.InsertEstudianteDialog;
import utils.InsertResiDialog;
import utils.InsertUniversidadDialog;

public class ResidenciasController implements Initializable {

	// FXML : View
	//---------------------------------------------
	
    @FXML
    private GridPane view;

    @FXML
    private TableView<Residencia> residenciaTable;

    @FXML
    private Button insertBt;

    @FXML
    private Button estanciasBt;

    @FXML
    private Button insertResiBt;

    @FXML
    private Button modifyResiBt;

    @FXML
    private Button removeResiBt;
    
    @FXML
    private TableColumn<Residencia, Boolean> comedorCol;
    
   //---------------------------------------------

    // Model
    //---------------------------------------------
    
    private ListProperty<Residencia> residenciasList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    public static ListProperty<Universidad> universidadesList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    // Necesitamos una referencia a nuestra aplicación principal
    private App myApp;
    
   //---------------------------------------------
    
	public ResidenciasController(App myApp) throws IOException {
		
		this.myApp = myApp;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResidenciasView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Obtenemos las universidades y las residencias de la base de datos
		universidadesList.addAll(HQLManager.getUniversidades());
		residenciasList.addAll(HQLManager.getResidencias());
		
		// Queremos que el comedor sea un checkbox
		comedorCol.setCellFactory(CheckBoxTableCell.forTableColumn(comedorCol));
		
		residenciaTable.itemsProperty().bind(residenciasList);
		
		// Eventos de los botones
		insertResiBt.setOnAction( evt -> onInsertResidencia() );
		removeResiBt.setOnAction( evt -> onDeleteResidencia() );
		modifyResiBt.setOnAction( evt -> onUpdateResidencia() );
		
		insertBt.setOnAction( evt -> insertTables() );
		
		estanciasBt.setOnAction( evt -> onGoEstancias() );
		
		modifyResiBt.disableProperty().bind(residenciaTable.getSelectionModel().selectedItemProperty().isNull());
		removeResiBt.disableProperty().bind(residenciaTable.getSelectionModel().selectedItemProperty().isNull());
	}
	
	private void onGoEstancias() {
		
		// Cambiamos de situación, ahora mostramos las estancias
		try {
			myApp.launchWindow(eWindowType.W_ESTANCIAS);
		} catch (IOException e) {
			App.launchException(e);
		}
	}

	private void onUpdateResidencia() {
		
		Residencia resi = residenciaTable.getSelectionModel().getSelectedItem();
		
		if( resi != null ) {
			
			String lastObs = (resi.getObservacion() != null ) ? resi.getObservacion().getObservaciones() : null;
			InsertResiDialog dialog = new InsertResiDialog(resi);
			dialog.initOwner(getRootView().getScene().getWindow());
			Optional<Residencia> myResi = dialog.showAndWait();
			
			if( myResi.isPresent() ) {
				HQLManager.updateResidencia(myResi.get());
				
				// Necesitamos refrescar la tabla en el caso de observaciones, puesto que no cambia
				// el objeto sino su contenido
				ResidenciasObservacion currentObs = null;
				if( lastObs != null && (currentObs = myResi.get().getObservacion() ) != null  ) {
					
					if( !currentObs.getObservaciones().equals(lastObs) ) {
						residenciaTable.refresh(); 
					}
						
				}		
			}
		}
	}

	private void onDeleteResidencia() {

		ButtonType yesBt = new ButtonType("Si", ButtonData.OK_DONE);
		ButtonType noBt = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getDialogPane().getButtonTypes().clear();
		alert.getDialogPane().getButtonTypes().addAll(yesBt, noBt);
		alert.initOwner(getRootView().getScene().getWindow());
		
		alert.setTitle("Confirmacion");
		alert.setHeaderText("Eliminar residencia");
		alert.setContentText("¿Está seguro de eliminar esta residencia?");
		
		Optional<ButtonType> response = alert.showAndWait();
		
		if( response.isPresent() && response.get() == yesBt ) {
			
			Residencia resi = residenciaTable.getSelectionModel().getSelectedItem();
			if( resi != null ) {
				residenciasList.remove(resi);
				HQLManager.removeResidencia(resi);
			}
		}
	}

	private void onInsertResidencia() {
		
		InsertResiDialog dialog = new InsertResiDialog();
		dialog.initOwner(getRootView().getScene().getWindow());
		Optional<Residencia> resi = dialog.showAndWait();
		
		if( resi.isPresent() ) {
			residenciasList.add(resi.get());
		}
	}

	private void insertTables() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Insercciones");
		alert.setHeaderText("¿Qué desea insertar?");
		
		ButtonType universidadBt = new ButtonType("Universidad");
		ButtonType estudiantesBt = new ButtonType("Estudiantes");
		ButtonType cancelBt = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(universidadBt, estudiantesBt, cancelBt);
		alert.initOwner(getRootView().getScene().getWindow());
		Optional<ButtonType> selection = alert.showAndWait();
		
		if( selection.isPresent() ) {
			
			if( selection.get().equals(universidadBt) ) {
				
				InsertUniversidadDialog dialog = new InsertUniversidadDialog();
				dialog.initOwner(getRootView().getScene().getWindow());
				Optional<Universidad> universidad = dialog.showAndWait();

				if (universidad.isPresent()) {

					Alert infoAlert = new Alert(AlertType.INFORMATION);
					infoAlert.setTitle("Listo");
					infoAlert.initOwner(getRootView().getScene().getWindow());
					infoAlert.setContentText("Universidad insertada");

					infoAlert.showAndWait();

					// Añadimos la universidad a la lista
					universidadesList.add(universidad.get());
				}
				
			} else if( selection.get().equals(estudiantesBt) ) {
				
				InsertEstudianteDialog dialog = new InsertEstudianteDialog();
				
				Optional<Estudiante> estudiante = dialog.showAndWait();
				
				if( estudiante.isPresent() ) {
					
					Alert infoAlert = new Alert(AlertType.INFORMATION);
					infoAlert.setTitle("Listo");
					infoAlert.initOwner(getRootView().getScene().getWindow());
					infoAlert.setContentText("Estudiante insertado");

					infoAlert.showAndWait();
				}
			}
		}
	}
	
	public GridPane getRootView() {
		return view;
	}

}
