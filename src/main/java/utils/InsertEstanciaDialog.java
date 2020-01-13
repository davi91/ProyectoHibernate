package utils;

import java.sql.Date;
import java.time.LocalDate;

import Main.App;
import clases.Estancia;
import clases.Residencia;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.geometry.HPos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class InsertEstanciaDialog extends Dialog<Estancia>{
	
	private class EstanciaValidation extends BooleanBinding {

		private ObjectExpression<Residencia> residencia;
		private StringExpression precio;
		
		
		public EstanciaValidation(ObjectExpression<Residencia> residencia, StringExpression precio) {
			this.residencia = residencia;
			this.precio = precio;
			
			bind(residencia, precio);
		}


		private boolean checkIsNumber(String n) {
			
			try {
				
				Integer.parseInt(n);
				
			} catch( NumberFormatException nEx) {
				return false;
			}
			
			return true;
		}
		
		@Override
		protected boolean computeValue() {
			
			if( residencia.get() == null || precio.get().isBlank() ) {
				return true;
			}
			
			if( !checkIsNumber(precio.get())) {
				return true;
			}
			
			return false;
		}
		
	}
	
	private final static String I_TITLE = "Insertar estancia";
	private final static String U_TITLE = "Actualizar estancia";
	private final static String I_HEADER = "Insertar nueva estancia";
	private final static String U_HEADER = "Actualizar estancia existente";
	
	private final static LocalDate OURDATE = LocalDate.now();
	
	private Estancia estanciaToUpdate;
	
	/**
	 * Insertamos una nueva estancia
	 */
	public InsertEstanciaDialog(App myApp) {
		this(false, null, null, 0, null, myApp);
	}
	
	public InsertEstanciaDialog(Estancia estancia, App myApp) {
		this(true, estancia.getFechaInicio(), estancia.getFechaFin(),
				estancia.getPrecioPagado(), estancia.getCodResidencia(), myApp);
		
		this.estanciaToUpdate = estancia;
	}
	
	public InsertEstanciaDialog(boolean bUpdate, Date fInicio, Date fFin, int precio, Residencia residencia, App myApp) {
		
		if( !bUpdate ) {
			setTitle(I_TITLE);
			setHeaderText(I_HEADER);
		} else {
			setTitle(U_TITLE);
			setHeaderText(U_HEADER);
		}
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label fInicioLbl = new Label("Fecha inicio:");
		DatePicker fInicioDate = new DatePicker(
				fInicio == null ? OURDATE : fInicio.toLocalDate());
		root.addRow(0, fInicioLbl, fInicioDate);
		
		Label fFinLbl = new Label("Fecha fin:");
		DatePicker fFinDate = new DatePicker(
				fFin == null ? LocalDate.of(OURDATE.getYear()+1, OURDATE.getMonth(), OURDATE.getDayOfMonth()) 
						     : fFin.toLocalDate() );
		root.addRow(1,  fFinLbl, fFinDate);
		
		Label precioLbl = new Label("Precio pagado:");
		TextField precioText = new TextField();
		precioText.setPromptText("Precio");
		precioText.setText(String.valueOf(precio));
		root.addRow(2,  precioLbl, precioText);
		
		Label resiLabel = new Label("Escoger una residencia");
		root.addRow(3,  resiLabel);
		GridPane.setColumnSpan(resiLabel, 2);
		GridPane.setHalignment(resiLabel, HPos.CENTER);
		GridPane.setHgrow(resiLabel, Priority.ALWAYS);
		
		ListView<Residencia> residencias = new ListView<>();
		residencias.getItems().setAll(myApp.getResidencias());
		root.addRow(4, residencias);
		GridPane.setColumnSpan(residencias, 2);
		residencias.setPrefHeight(128.f);
		GridPane.setHalignment(residencias, HPos.CENTER);
		GridPane.setHgrow(residencias, Priority.ALWAYS);
		
		if( residencia != null ) {
			residencias.getSelectionModel().select(residencia);
		}
		
		getDialogPane().setContent(root);
		
		ButtonType okButton = new ButtonType(!bUpdate ? "Insertar" : "Actualizar", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		getDialogPane().lookupButton(okButton).disableProperty().bind(
				new EstanciaValidation(residencias.getSelectionModel().selectedItemProperty(),
									   precioText.textProperty()));
		
		setResultConverter( bt -> {
			
			if( bt == okButton ) {
				
				if( !bUpdate ) {
					return new Estancia(residencias.getSelectionModel().getSelectedItem(),
						Date.valueOf(fInicioDate.getValue()), Date.valueOf(fFinDate.getValue()), Integer.parseInt(precioText.getText()));
				}
				
				else {
					estanciaToUpdate.updateEstancia(residencias.getSelectionModel().getSelectedItem(), 
							Date.valueOf(fInicioDate.getValue()), Date.valueOf(fFinDate.getValue()), Integer.parseInt(precioText.getText()));
					
					return estanciaToUpdate;
				}
			}
			
			return null;
		});
	}
	

}
