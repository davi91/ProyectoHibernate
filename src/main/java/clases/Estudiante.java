package clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import utils.HQLManager;

@Entity
@Table(name="estudiantes")
public class Estudiante {

	public Estudiante() {
		
	}
	

	public Estudiante(String dni, String nomEstudiante, String telefonoEstudiante) {
		setDni(dni);
		setNomEstudiante(nomEstudiante);
		setTelefonoEstudiante(telefonoEstudiante);
		
		HQLManager.insertEstudiante(this);
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codEstudiante;
	
	@Column(length = 9)
	private String dni;
	
	@Column(length = 50)
	private String nomEstudiante;
	
	@Column(length = 9)
	private String telefonoEstudiante;

	public int getCodEstudiante() {
		return codEstudiante;
	}

	public void setCodEstudiante(int codEstudiante) {
		this.codEstudiante = codEstudiante;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNomEstudiante() {
		return nomEstudiante;
	}

	public void setNomEstudiante(String nomEstudiante) {
		this.nomEstudiante = nomEstudiante;
	}

	public String getTelefonoEstudiante() {
		return telefonoEstudiante;
	}

	public void setTelefonoEstudiante(String telefonoEstudiante) {
		this.telefonoEstudiante = telefonoEstudiante;
	}
	
	@Override
	public String toString() {
		return dni;
	}
}
