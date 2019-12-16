package ui;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import clases.Residencia;
import clases.Universidad;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	}
	
	private void onInsertResidencia() {
		
		InsertResiDialog dialog = new InsertResiDialog();
		
		Optional<Residencia> resi = dialog.showAndWait();
	}

	public GridPane getRootView() {
		return view;
	}

}
