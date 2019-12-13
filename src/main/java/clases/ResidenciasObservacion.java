package clases;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name="residenciasobservaciones")
public class ResidenciasObservacion implements Externalizable {

	private IntegerProperty codResidencia;
	private StringProperty observaciones;
	private ObjectProperty<Residencia> codFResindecia;
	
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(codFResindecia.getValue());
		out.writeObject(observaciones.getValue());
		out.writeInt(codResidencia.getValue());
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		codFResindecia.set((Residencia)in.readObject());
		observaciones.setValue((String)in.readObject());
		codResidencia.setValue(in.readInt());
	}

	public final ObjectProperty<Residencia> codFResindeciaProperty() {
		
		if( codFResindecia == null ) {
			codFResindecia = new SimpleObjectProperty<Residencia>(this, "codFResindecia", _codFResindecia);
		}
		
		return this.codFResindecia;
	}
	

	private Residencia _codFResindecia;
	@OneToOne(cascade= {CascadeType.PERSIST})
	@PrimaryKeyJoinColumn
	public final Residencia getCodFResindecia() {
		
		if( this.codFResindecia == null ) {
			return _codFResindecia;
		}
		
		return this.codFResindeciaProperty().get();
	}
	

	public final void setCodFResindecia(final Residencia codFResindecia) {
		
		if( this.codFResindecia == null ) {
			_codFResindecia = codFResindecia;
		}
		
		else {
			this.codFResindeciaProperty().set(codFResindecia);
		}
	}
	
	private int _codResidencia;
	@Id
	@GeneratedValue(generator = "resForeign")
	@GenericGenerator(name ="resForeign", strategy = "foreign",
					  parameters = {@org.hibernate.annotations.Parameter(
					  name = "property", value = "codFResindecia") })
	public final int getCodResidencia() {
		
		if( this.codResidencia == null ) {
			return _codResidencia;
		}
		
		return this.codResidenciaProperty().get();
	}

	public final void setCodResidencia(int codResidencia) {
		
		if( this.codResidencia == null ) {
			_codResidencia = codResidencia;
		}
		
		else {
			this.codResidenciaProperty().set(codResidencia);
		}
	}
	
	public final IntegerProperty codResidenciaProperty() {
		
		if( this.codResidencia == null ) {
			this.codResidencia = new SimpleIntegerProperty(this, "codResidencia", _codResidencia);
		}
		
		return codResidencia;
		
	}

	private String _observaciones;
	@Column(length = 200)
	public final String getObservaciones() {
		
		if( this.observaciones == null ) {
			return _observaciones;
		}
		
		return observacionesProperty().get();
	}

	public void setObservaciones(String observaciones) {
		
		if( this.observaciones == null ) {
			_observaciones = observaciones;
		}
		
		else {
			
			this.observacionesProperty().set(observaciones);
		}
		
	}
	
	public final StringProperty observacionesProperty() {
		
		if( this.observaciones == null ) {
			this.observaciones = new SimpleStringProperty(this, "observaciones", _observaciones);
		}
		
		return observaciones;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getObservaciones();
	}
	
}
