package utils;

import clases.Universidad;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringExpression;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class InsertUniversidadDialog extends Dialog<Universidad> {

	private class UniversidaDialogValidation extends BooleanBinding {

		private StringExpression codUniversidad;
		private StringExpression nomUniversidad;
		
		
		public UniversidaDialogValidation(StringExpression codUniversidad, StringExpression nomUniversidad) {
			this.codUniversidad = codUniversidad;
			this.nomUniversidad = nomUniversidad;
			
			bind(codUniversidad, nomUniversidad);
		}


		@Override
		protected boolean computeValue() {
		
			if( codUniversidad.get() == null || nomUniversidad.get() == null ) {
				return true;
			}
			
			if( codUniversidad.get().length() != 6 || nomUniversidad.get().isBlank()) {
				return true;
			}
			
			return false;
		}
		
	}
	
	public InsertUniversidadDialog() {
		
		setTitle("Insertar universidad");
		setHeaderText("Insertar nueva universidad");
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label codLabel = new Label("Código:");
		TextField codTxt = new TextField();
		codTxt.setPromptText("6 caracteres");
		root.addRow(0, codLabel, codTxt);
		
		Label nombreLbl = new Label("Nombre");
		TextField nomTxt = new TextField();
		nomTxt.setPromptText("Nombre universidad");
		root.addRow(1, nombreLbl, nomTxt);
		
		ButtonType okButton = new ButtonType("Añadir", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

		getDialogPane().setContent(root);
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		
		getDialogPane().lookupButton(okButton).disableProperty().bind(
				new UniversidaDialogValidation(codTxt.textProperty(), nomTxt.textProperty()));
		
		setResultConverter( bt -> {
			
			if( bt == okButton ) {
				return new Universidad(codTxt.getText(), nomTxt.getText());
			}
			
			return null;
		});
	}
}
