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
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import ui.ResidenciasController;

public class InsertResiDialog extends Dialog<Residencia> {

	public class ResiDialogValidation extends BooleanBinding {

		// Ponemos los campos obligatorios
		private StringExpression nombre;
		private StringExpression precio;
		private ObjectExpression<Universidad> universidad;
		
		public ResiDialogValidation(StringExpression nombre, ObjectExpression<Universidad> universidad, StringExpression precio) {
			super();
			this.nombre = nombre;
			this.universidad = universidad;
			this.precio = precio;
			bind(this.nombre, this.universidad, this.precio);
		}

		private boolean checkPrecioString() {
			
			try {
				 
				Integer.parseInt(precio.get());
				
			} catch( NumberFormatException e ) {
				return false;
			}
			
			return true;
		}

		@Override
		protected boolean computeValue() {
			
			if( nombre.get() == null || universidad.get() == null || precio.get() == null ) {
				return false;
			}
			
			if( nombre.get().isEmpty() || !checkPrecioString() ) {
				return false;
			}

			return true;
		}
		
		
	}
	
	public InsertResiDialog() {
		this(null, null, "0", false, null);
	}
	
	public InsertResiDialog(String nombreResi, Universidad universidadObj, String precioResi, boolean comedorResi, String observacionTxt) {
		
		setTitle("Insertar residencia");
		setHeaderText("Datos de la residencia a insertar");
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label nombreLbl = new Label("Nombre:");
		TextField nombre = new TextField();
		nombre.setPromptText("Nombre de la residencia");
		if( nombreResi != null ) {
			nombre.setText(nombreResi);
		}
		root.addRow(0, nombreLbl, nombre);
		
		Label universidadLbl = new Label("Universidades:");
		ComboBox<Universidad> universidades = new ComboBox<Universidad>();
		universidades.itemsProperty().bind( ResidenciasController.universidadesList);
		universidades.setPromptText("Seleccione universidad");
		if( universidadObj != null ) {
			universidades.getSelectionModel().select(universidadObj);
		}
		root.addRow(1,  universidadLbl, universidades);

		
		Label precioLbl = new Label("Precio:");
		TextField precio = new TextField();
		precio.setPromptText("Precio residencia");
		precio.setText(precioResi);
		root.addRow(2, precioLbl, precio);
		
		Label comedorLbl = new Label("Comedor:");
		CheckBox comedor = new CheckBox();
		comedor.setSelected(comedorResi);
		root.addRow(3, comedorLbl, comedor);
		
		Label observacionLbl = new Label("Observacion:");
		TextArea observacion = new TextArea();
		observacion.setPromptText("Observacion");
		if( observacionTxt != null ) {
			observacion.setText(observacionTxt);
		}
		
		root.addRow(4, observacionLbl, observacion);
		
		getDialogPane().setContent(root);
		
		ButtonType okButton = new ButtonType("Insertar", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		getDialogPane().lookupButton(okButton).disableProperty().bind(new ResiDialogValidation(nombre.textProperty(),
																universidades.getSelectionModel().selectedItemProperty(),
																precio.textProperty()).not());
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
