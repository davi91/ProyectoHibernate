package utils;

import clases.Estudiante;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringExpression;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InsertEstudianteDialog extends Dialog<Estudiante> {

	private class EstudianteDialogValidation extends BooleanBinding {

		private StringExpression dni;
		private StringExpression nombre;
		private StringExpression telefono;
		
		
		public EstudianteDialogValidation(StringExpression dni, StringExpression nombre, StringExpression telefono) {
			this.dni = dni;
			this.nombre = nombre;
			this.telefono = telefono;
			bind(dni, nombre, telefono);
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
			
			return false;
		}
		
	}
	public InsertEstudianteDialog() {
		
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
		
		ButtonType okButton = new ButtonType("Añadir estudiante", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().setContent(root);
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		
		getDialogPane().lookupButton(okButton).disableProperty().bind(
				new EstudianteDialogValidation(dniTxt.textProperty(),
						nomTxt.textProperty(), tlfTxt.textProperty()));
		
		setResultConverter( bt -> {
			
			if( bt == okButton ) {
				return new Estudiante(dniTxt.getText(), nomTxt.getText(), tlfTxt.getText());
			}
			
			return null;
		});
		
		
		
	}
}
