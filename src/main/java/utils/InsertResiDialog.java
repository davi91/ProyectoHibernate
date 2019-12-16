package utils;

import clases.Residencia;
import clases.Universidad;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import ui.ResidenciasController;

public class InsertResiDialog extends Dialog<Residencia> {

	public InsertResiDialog() {
		
		setTitle("Insertar residencia");
		setHeaderText("Datos de la residencia a insertar");
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label nombreLbl = new Label("Nombre:");
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre de la residencia");
		root.addRow(0, nombreLbl, nombre);
		
		Label universidadLbl = new Label("Universidades:");
		ComboBox<Universidad> universidades = new ComboBox<Universidad>();
		universidades.itemsProperty().bind( ResidenciasController.universidadesList);
		universidades.setPromptText("Seleccione universidad");
		root.addRow(1,  universidadLbl, universidades);
		
		Label precioLbl = new Label("Precio:");
		TextField precio = new TextField();
		precio.setPromptText("Precio residencia");
		root.addRow(2, precioLbl, precio);
		
		Label comedorLbl = new Label("Comedor:");
		CheckBox comedor = new CheckBox();
		root.addRow(3, comedorLbl, comedor);
		
		Label observacionLbl = new Label("Observacion:");
		TextArea observacion = new TextArea();
		observacion.setPromptText("Observacion");
		root.addRow(4, observacionLbl, observacion);
		
		getDialogPane().setContent(root);
		
		ButtonType okButton = new ButtonType("Insertar", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		
		setResultConverter( bt -> {
			
			if( bt == okButton ) {
				
				Residencia resi = new Residencia(nombre.getText(), Integer.parseInt(precio.getText()), comedor.isSelected(),
											    observacion.getText(), universidades.getSelectionModel().getSelectedItem());
				return resi;
			}
			
			return null;
		});
	}
}
