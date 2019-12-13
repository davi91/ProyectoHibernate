package clases;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="estancias")
public class Estancia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codEstancia;
	
	private int codEstudiante;
	
	private int codResidencia;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	@Column(columnDefinition = "smallint(6)")
	private int precioPagado;

	public int getCodEstancia() {
		return codEstancia;
	}

	public void setCodEstancia(int codEstancia) {
		this.codEstancia = codEstancia;
	}

	public int getCodEstudiante() {
		return codEstudiante;
	}

	public void setCodEstudiante(int codEstudiante) {
		this.codEstudiante = codEstudiante;
	}

	public int getCodResidencia() {
		return codResidencia;
	}

	public void setCodResidencia(int codResidencia) {
		this.codResidencia = codResidencia;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getPrecioPagado() {
		return precioPagado;
	}

	public void setPrecioPagado(int precioPagado) {
		this.precioPagado = precioPagado;
	}
}
