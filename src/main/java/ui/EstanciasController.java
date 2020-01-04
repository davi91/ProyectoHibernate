package ui;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import Main.App;
import Main.App.eWindowType;
import clases.Estancia;
import clases.Estudiante;
import clases.Residencia;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class EstanciasController implements Initializable {

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

    }

    @FXML
    void insertTables(ActionEvent event) {

    }

    @FXML
    void removeEstancia(ActionEvent event) {

    }

    @FXML
    void updateEstancia(ActionEvent event) {

    }
    
    public GridPane getRootView() {
		return view;
	}
}
