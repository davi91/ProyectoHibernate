package ui;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Residencia;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class ResidenciasController implements Initializable {

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

	public ResidenciasController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResidenciasView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public GridPane getRootView() {
		return view;
	}

}
