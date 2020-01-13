package utils;

import Main.App;
import clases.Estancia;
import clases.Estudiante;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ui.EstanciasController;

public class InsertEstudianteDialog extends Dialog<Estudiante> {

	private class EstudianteDialogValidation extends BooleanBinding {

		private StringExpression dni;
		private StringExpression nombre;
		private StringExpression telefono;
		private ObjectExpression<Estancia> estancia;
		
		public EstudianteDialogValidation(StringExpression dni, StringExpression nombre, StringExpression telefono,
										  ObjectExpression<Estancia> estancia) {
			this.dni = dni;
			this.nombre = nombre;
			this.telefono = telefono;
			this.estancia = estancia;
			
			bind(dni, nombre, telefono, estancia);
		}


		@Override
		protected boolean computeValue() {
		
			if( dni.get() == null || nombre.get() == null  || telefono.get() == null ) {
				return true;
			}
			
			if( dni.get().isBlank() || nombre.get().isBlank() || telefono.get().isBlank() ) {
				return true;
			}
			
			if( dni.get().length() != 9 || telefono.get().length() != 9 ) {
				return true;
			}
			
			if( estancia.get() == null ) {
				return true;
			}
			
			return false;
		}
		
	}
	public InsertEstudianteDialog(App myApp) {
		
		setTitle("Insertar estudiante");
		setHeaderText("Insertar nuevo estudiante");
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label dniLbl = new Label("DNI:");
		TextField dniTxt = new TextField();
		dniTxt.setPromptText("9 caracteres");
		root.addRow(0, dniLbl, dniTxt);
		
		Label nomLbl = new Label("Nombre:");
		TextField nomTxt = new TextField();
		nomTxt.setPromptText("Nombre estudiante");
		root.addRow(1,  nomLbl, nomTxt);
		
		Label tlfLbl = new Label("Telefono:");
		TextField tlfTxt = new TextField();
		tlfTxt.setPromptText("9 caracteres");
		root.addRow(2,  tlfLbl, tlfTxt);
		
		Label estanciasLbl = new Label("Estancia");
		ComboBox<Estancia> estanciaCb = new ComboBox<>();
		estanciaCb.getItems().setAll(myApp.getEstancias());
		root.addRow(3,  estanciasLbl, estanciaCb);
		
		ButtonType okButton = new ButtonType("Añadir estudiante", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().setContent(root);
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		
		getDialogPane().lookupButton(okButton).disableProperty().bind(
				new EstudianteDialogValidation(dniTxt.textProperty(),
						nomTxt.textProperty(), tlfTxt.textProperty(), estanciaCb.valueProperty()));
		
		setResultConverter( bt -> {
			
			if( bt == okButton ) {
				return new Estudiante(dniTxt.getText(), nomTxt.getText(), tlfTxt.getText(),
									estanciaCb.getSelectionModel().getSelectedItem());
			}
			
			return null;
		});
		
		
		
	}
}
