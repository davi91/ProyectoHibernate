package ui;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Main.HibernateUtil;
import clases.Residencia;
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
import utils.InsertResiDialog;

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
    private Button advancedBt;

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
   //---------------------------------------------
    
	public ResidenciasController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResidenciasView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Obtenemos las universidades de la base de datos
		universidadesList.addAll(HQLManager.getUniversidades());
		
		// Queremos que el comedor sea un checkbox
		comedorCol.setCellFactory(CheckBoxTableCell.forTableColumn(comedorCol));
		
		// Obtenemos las residencias y las colocamos en la tabla
		residenciasList.addAll(HQLManager.getResidencias());
		residenciaTable.itemsProperty().bind(residenciasList);
		
		// Eventos de los botones
		insertResiBt.setOnAction( evt -> onInsertResidencia() );
		removeResiBt.setOnAction( evt -> onDeleteResidencia() );
		modifyResiBt.setOnAction( evt -> onUpdateResidencia() );
		
		modifyResiBt.disableProperty().bind(residenciaTable.getSelectionModel().selectedItemProperty().isNull());
		removeResiBt.disableProperty().bind(residenciaTable.getSelectionModel().selectedItemProperty().isNull());
	}
	
	private void onUpdateResidencia() {
		
		Residencia resi = residenciaTable.getSelectionModel().getSelectedItem();
		
		if( resi != null ) {
			
			InsertResiDialog dialog = new InsertResiDialog(resi.getNomResidencia(), resi.getUniversidad(), 
														String.valueOf(resi.getPrecioMensual()), resi.getComedor(), resi.getObservacion().toString());
			
			Optional<Residencia> myResi = dialog.showAndWait();
			
			if( myResi.isPresent() ) {
				HQLManager.updateResidencia(myResi.get());
			}
		}
	}

	private void onDeleteResidencia() {

		ButtonType yesBt = new ButtonType("Si", ButtonData.OK_DONE);
		ButtonType noBt = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getDialogPane().getButtonTypes().clear();
		alert.getDialogPane().getButtonTypes().addAll(yesBt, noBt);
		
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
		
		Optional<Residencia> resi = dialog.showAndWait();
		
		if( resi.isPresent() ) {
			residenciasList.add(resi.get());
		}
	}

	public GridPane getRootView() {
		return view;
	}

}
