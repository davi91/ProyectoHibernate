package Main;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import clases.Estancia;
import clases.Residencia;
import clases.ResidenciasObservacion;
import clases.Universidad;

public class Main {

	public static void main(String[] args) {
		
		// Creamos la base de datos
	//	Session session = HibernateUtil.getSessionFactory().openSession();
		
		// Abrimos la transacción
	//	Transaction transaction = session.beginTransaction();
		
		/*
		Universidad uni = new Universidad();
		uni.setCodUniversidad("000001");
		uni.setNomUniversidad("Nombre uni");
		
		Residencia resi = new Residencia();
		resi.setNomResidencia("Nombre resi");
		resi.setComedor(0);
		resi.setPrecioMensual(1200);
		resi.setUniversidad(uni);
		
		ResidenciasObservacion observacion = new ResidenciasObservacion();
		observacion.setCodFResindecia(resi);
		observacion.setObservaciones("Esta es una observacion");
		
		resi.setObservacion(observacion);
		
		session.save(uni);
		session.save(observacion);
		session.save(resi);
		*/
		
		// Importamos la universidad

//		transaction.commit();
	//	session.close();
		
		// Ahora, una vez creada la base de datos, procedemos a la interfaz
		App.main(args);
	}

}
