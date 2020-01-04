package clases;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.HQLManager;

@Entity
@Table(name="residencias")
public class Residencia implements Externalizable {
	
	private int _codResidencia;
	private IntegerProperty codResidencia;
	
	private String _nomResidencia;
	private StringProperty nomResidencia;
	
	// Una universidad puede tener varias residencias pero una
	// residencia solo pertenece a una universidad
	private Universidad _universidad;
	private ObjectProperty<Universidad> universidad;
	
	// OneToOne bidireccional, una residencia puede no tener observación
	// pero una observación siempre tiene que tener residencia
	private ResidenciasObservacion _observacion;
	private ObjectProperty<ResidenciasObservacion> observacion;
	
	private int _precioMensual;
	private IntegerProperty precioMensual;
	
	private boolean _comedor;
	private BooleanProperty comedor;

	public Residencia() {
		
	}
	
	public void updateResidencia(String nombre, int precio, boolean comedor, String observacion, Universidad universidad) {
		
		setNomResidencia(nombre);
		setPrecioMensual(precio);
		setComedor(comedor);
		setUniversidad(universidad);
		
		if( getObservacion() != null ) {
			getObservacion().setObservaciones(observacion);
			HQLManager.updateObservacion(getObservacion());
			
		} else {
			
			// Podría pasar que añadimos una nueva observación
			if( observacion != null ) {
				ResidenciasObservacion obObj = new ResidenciasObservacion(observacion);
				obObj.setCodFResindecia(this); // Enlazamos con esta residencia
				setObservacion(obObj);
				HQLManager.insertObservacion(obObj);
			}
		}
	}
	
	public Residencia(String nombre, int precio, boolean comedor, String observacion, Universidad universidad) {
	
		setNomResidencia(nombre);
		setPrecioMensual(precio);
		setComedor(comedor);
		setUniversidad(universidad);

		ResidenciasObservacion obObj = null;
		// Ahora necesitamos asociarla a una observación
		
		if (observacion != null) {

			obObj = new ResidenciasObservacion(observacion);
			obObj.setCodFResindecia(this); // Enlazamos con esta residencia
			setObservacion(obObj);
		}

		// Primero insertamos la residencia y luego la observacion
		HQLManager.insertResidencia(this);

		if (obObj != null)
			HQLManager.insertObservacion(obObj);
			
	}
	
	public final BooleanProperty comedorProperty() {
		
		if( this.comedor == null ) {
			this.comedor = new SimpleBooleanProperty(this, "comedor", _comedor);
		}
		
		return this.comedor;
	}
	
	public final void setComedor(boolean comedor) {
		
		if( this.comedor == null ) {
			_comedor = comedor;
		} else {
			this.comedor.set(comedor);
		}
	}
	
	@Column
	public final boolean getComedor() {
		
		if( this.comedor == null ) {
			return _comedor;
		}
		
		return this.comedor.get();
	}
	
	public final StringProperty nomResidenciaProperty() {
		
		if( this.nomResidencia == null ) {
			this.nomResidencia = new SimpleStringProperty(this, "nomResidencia", _nomResidencia);
		}
		
		return this.nomResidencia;
	}
	

	@Column(length = 30)
	public final String getNomResidencia() {
		
		if( this.nomResidencia == null ) {
			return _nomResidencia;
		}
		
		return this.nomResidenciaProperty().get();
	}
	

	public final void setNomResidencia(final String nomResidencia) {
		
		if( this.nomResidencia == null ) {
			_nomResidencia = nomResidencia;
		}
		
		else {
			this.nomResidenciaProperty().set(nomResidencia);
		}
	}
	

	public final ObjectProperty<Universidad> universidadProperty() {
		
		if( this.universidad == null ) {
			this.universidad = new SimpleObjectProperty<Universidad>(this, "universidad", _universidad);
		}
		
		return this.universidad;
	}
	

	@ManyToOne(fetch = FetchType.EAGER) // Me interesa obtener la universidad asociada, puesto que la necesitamos en la tabla
	@JoinColumn(name="codUniversidad")
	public final Universidad getUniversidad() {
		
		if( this.universidad == null ) {
			return _universidad;
		}
		
		return this.universidadProperty().get();
	}
	

	public final void setUniversidad(final Universidad universidad) {
		
		if( this.universidad == null ) {
			_universidad = universidad;
		}
		
		else {
			this.universidadProperty().set(universidad);
		}
	}
	

	public final ObjectProperty<ResidenciasObservacion> observacionProperty() {
		
		if( this.observacion == null ) {
			this.observacion = new SimpleObjectProperty<ResidenciasObservacion>(this, "observacion", _observacion);
		}
		
		return this.observacion;
	}
	

	@OneToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@PrimaryKeyJoinColumn
	public final ResidenciasObservacion getObservacion() {
		
		if( this.observacion == null ) {
			return _observacion;
		}
		
		return this.observacionProperty().get();
	}
	

	public final void setObservacion(final ResidenciasObservacion observacion) {
		
		if( this.observacion == null ) {
			_observacion = observacion;
		}
		
		else {	
			this.observacionProperty().set(observacion);	
		}
	}


	public final IntegerProperty precioMensualProperty() {
		
		if( this.precioMensual == null ) {
			this.precioMensual = new SimpleIntegerProperty(this, "precioMensual", _precioMensual);
		}
		
		return this.precioMensual;
	}
	

	
	@Column(columnDefinition = "smallint(6)")
	public final int getPrecioMensual() {
		
		if( precioMensual == null ) {
			return _precioMensual;
		}
		
		return this.precioMensualProperty().get();
	}
	


	public final void setPrecioMensual(final int precioMensual) {
		
		if( this.precioMensual == null ) {
			_precioMensual = precioMensual;
		}
		
		else {
			this.precioMensualProperty().set(precioMensual);
		}
		
	}

	public final IntegerProperty codResidenciaProperty() {
		
		if( this.codResidencia == null ) {
			this.codResidencia = new SimpleIntegerProperty(this, "codResidencia", _codResidencia);
		}
		
		return this.codResidencia;
	}
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public final int getCodResidencia() {
		
		if( this.codResidencia == null ) {
			return _codResidencia;
		}
		
		return this.codResidenciaProperty().get();
	}
	

	public final void setCodResidencia(final int codResidencia) {
		
		if( this.codResidencia == null ) {
			_codResidencia = codResidencia;
		}
		
		else {
			this.codResidenciaProperty().set(codResidencia);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeInt(getCodResidencia());
		out.writeBoolean(getComedor());
		out.writeInt(getPrecioMensual());
		out.writeObject(getNomResidencia());
		out.writeObject(getObservacion());
		out.writeObject(getUniversidad());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		
		setCodResidencia(in.readInt());
		setComedor(in.readBoolean());
		setPrecioMensual(in.readInt());
		setNomResidencia((String)in.readObject());
		setObservacion((ResidenciasObservacion)in.readObject());
		setUniversidad((Universidad)in.readObject());
		
	}
	
	
	
	
	

}
