package clases;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.protobuf.Value;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import utils.HQLManager;

@Entity
@Table(name="estancias")
public class Estancia implements Externalizable {

	private int _codEstancia;
	private IntegerProperty codEstancia;
	
	private List<Estudiante> _estudiantes = new ArrayList<>();
	private ListProperty<Estudiante> estudiantes;
	
	private Residencia _codResidencia;
	private ObjectProperty<Residencia> codResidencia;
	
	private Date _fechaInicio;
	private ObjectProperty<Date> fechaInicio;
	
	private Date _fechaFin;
	private ObjectProperty<Date> fechaFin;
	
	private int _precioPagado;
	private IntegerProperty precioPagado;
	
	public Estancia() {
		
	}
	
	public void updateEstancia( Residencia residencia, Date fechaInicio,
					Date fechaFin, int precioPagado) {
		
		setCodResidencia(residencia);
		setFechaInicio(fechaInicio);
		setFechaFin(fechaFin);
		setPrecioPagado(precioPagado);
		
		HQLManager.actualizarEstancia(this);
		
	}
	
	public Estancia(Residencia residencia, Date fechaInicio,
					Date fechaFin, int precioPagado) {
		
		setCodResidencia(residencia);
		setFechaInicio(fechaInicio);
		setFechaFin(fechaFin);
		setPrecioPagado(precioPagado);
		
		// Ahora creamos el objeto en la base de datos
		HQLManager.insertarEstancia(this);
		
	}

	public final IntegerProperty codEstanciaProperty() {
		
		if( this.codEstancia == null ) {
			this.codEstancia = new SimpleIntegerProperty(this, "codEstancia", _codEstancia);
		}
		
		return this.codEstancia;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public final int getCodEstancia() {
		
		if( this.codEstancia == null ) {
			return this._codEstancia;
		}
		
		return this.codEstanciaProperty().get();
	}
	
	public final void setCodEstancia(final int codEstancia) {
		
		if( this.codEstancia == null ) {
			this._codEstancia = codEstancia;
		}
		
		else {
			this.codEstanciaProperty().set(codEstancia);
		}
	}
	
	public final ListProperty<Estudiante> estudiantesProperty() {
		
		if( this.estudiantes == null ) {
			
			this.estudiantes = new SimpleListProperty<Estudiante>(
					FXCollections.observableArrayList(_estudiantes));
		}
		
		return this.estudiantes;
	}
	
	
	/*
	 * Cuando es @OneToMany, se recomienda mucho que sea bidireccional,
	 * si no los resultados pueden llegar a a ser inestables
	 */
	 @OneToMany(cascade=CascadeType.ALL,
			 	fetch=FetchType.EAGER, mappedBy="estancia")
	public final List<Estudiante> getEstudiantes() {
		
		if( this.estudiantes == null ) {
			return this._estudiantes;
		}
		
		return this.estudiantesProperty().get();
	}
	
	public final void setEstudiantes(final List<Estudiante> estudiantes) {
		
		if( this.estudiantes == null ) {
			this._estudiantes = estudiantes;
		}
		
		else {
			this.estudiantesProperty().set(FXCollections.observableArrayList(estudiantes));
		}
	}
	
	public final ObjectProperty<Residencia> codResidenciaProperty() {
	
		if( this.codResidencia == null ) {
			this.codResidencia = new SimpleObjectProperty<Residencia>(this, "codResidencia", _codResidencia);
		}
		
		return this.codResidencia;
	}
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "codResidencia")
	public final Residencia getCodResidencia() {
		
		if( this.codResidencia == null ) {
			return this._codResidencia;
		}
		
		return this.codResidenciaProperty().get();
	}
	
	public final void setCodResidencia(final Residencia codResidencia) {
		
		if( this.codResidencia == null ) {
			this._codResidencia = codResidencia;
		}
		
		else {
			this.codResidenciaProperty().set(codResidencia);
		}
	}
	
	public final ObjectProperty<Date> fechaInicioProperty() {
		
		if( this.fechaInicio == null ) {
			this.fechaInicio = new SimpleObjectProperty<Date>(this, "fechaInicio", _fechaInicio);
		}
		
		return this.fechaInicio;
	}
	
	public final Date getFechaInicio() {
		
		if( this.fechaInicio == null ) {
			return this._fechaInicio;
		}
		
		return this.fechaInicioProperty().get();
	}
	
	public final void setFechaInicio(final Date fechaInicio) {
		
		if( this.fechaInicio == null ) {
			this._fechaInicio = fechaInicio;
		}
		
		else {
			this.fechaInicioProperty().set(fechaInicio);
		}
	}
	
	public final ObjectProperty<Date> fechaFinProperty() {
		
		if( this.fechaFin == null ) {
			this.fechaFin = new SimpleObjectProperty<Date>(this, "fechaFin", _fechaFin);
		}
		
		return this.fechaFin;
	}
	
	public final Date getFechaFin() {
		
		if( this.fechaFin == null ) {
			return this._fechaFin;
		}
		
		return this.fechaFinProperty().get();
	}
	
	public final void setFechaFin(final Date fechaFin) {
		
		if( this.fechaFin == null ) {
			this._fechaFin = fechaFin;
		}
		
		else {
			this.fechaFinProperty().set(fechaFin);
		}
	}
	
	public final IntegerProperty precioPagadoProperty() {
		
		if( this.precioPagado == null ) {
			this.precioPagado = new SimpleIntegerProperty(this, "precioPagado", _precioPagado);
		}
		
		return this.precioPagado;
	}
	
	@Column(columnDefinition = "smallint(6)")
	public final int getPrecioPagado() {
		
		if( this.precioPagado == null ) {
			return this._precioPagado;
		}
		
		return this.precioPagadoProperty().get();
	}
	
	public final void setPrecioPagado(final int precioPagado) {
		
		if( this.precioPagado == null ) {
			this._precioPagado = precioPagado;
		}
		
		else {
			this.precioPagadoProperty().set(precioPagado);
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s a %s, %s",getFechaInicio().toString(), getFechaFin().toString(), getCodResidencia().toString());
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		
		out.writeInt(getCodEstancia());
		out.writeObject(getEstudiantes());
		out.writeObject(getCodResidencia());
		out.writeObject(getFechaInicio());
		out.writeObject(getFechaFin());
		out.writeInt(getPrecioPagado());	
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		
		setCodEstancia(in.readInt());
		setEstudiantes((List<Estudiante>)in.readObject());
		setCodResidencia((Residencia)in.readObject());
		setFechaInicio((Date)in.readObject());
		setFechaFin((Date)in.readObject());
		setPrecioPagado(in.readInt());
		
	}
	
	
	

}
