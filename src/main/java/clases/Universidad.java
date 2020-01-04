package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import utils.HQLManager;

@Entity
@Table(name="universidades")
public class Universidad {

	public Universidad() {
		
	}
	
	public Universidad(String codUniversidad, String nombreUniversidad) {
		
		setCodUniversidad(codUniversidad);
		setNomUniversidad(nombreUniversidad);
		
		HQLManager.insertUniversidad(this);
	}
	
	@Id
	@Column(columnDefinition = "char(6)")
	private String codUniversidad;
	
	@Column(length = 30)
	private String nomUniversidad;

	public String getCodUniversidad() {
		return codUniversidad;
	}

	public void setCodUniversidad(String codUniversidad) {
		this.codUniversidad = codUniversidad;
	}

	public String getNomUniversidad() {
		return nomUniversidad;
	}

	public void setNomUniversidad(String nomUniversidad) {
		this.nomUniversidad = nomUniversidad;
	}
	
	@Override
	public String toString() {
		return nomUniversidad;
	}
}
