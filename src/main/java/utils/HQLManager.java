package utils;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Main.HibernateUtil;
import clases.Estancia;
import clases.Estudiante;
import clases.Residencia;
import clases.ResidenciasObservacion;
import clases.Universidad;

public class HQLManager {

	@SuppressWarnings("unchecked")
	public static List<Residencia> getResidencias() {

		// Creamos la base de datos
		Session session = HibernateUtil.getSessionFactory().openSession();

		// Abrimos la transacción
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("from Residencia");

		List<Residencia> resis = null;
		resis = query.getResultList();

		transaction.commit(); // La cerramos
		session.close(); // Hemos terminado

		return resis;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Estancia> getEstancias() {
		
		// Creamos la base de datos
		Session session = HibernateUtil.getSessionFactory().openSession();

		// Abrimos la transacción
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("from Estancia");

		List<Estancia> estancias = null;
		estancias = query.getResultList();

		transaction.commit(); // La cerramos
		session.close(); // Hemos terminado

		return estancias;
	}

	@SuppressWarnings("unchecked")
	public static List<Universidad> getUniversidades() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("from Universidad");

		List<Universidad> unis = null;
		unis = query.getResultList();

		transaction.commit();
		session.close();

		return unis;
	}

	public static void insertResidencia(Residencia residencia) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.save(residencia);

		transaction.commit();
		session.close();
	}

	public static void removeResidencia(Residencia residencia) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.delete(residencia);

		transaction.commit();
		session.close();
	}

	public static void updateResidencia(Residencia residencia) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.update(residencia);
		transaction.commit();
		session.close();

	}

	public static void insertObservacion(ResidenciasObservacion observacion) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.save(observacion);

		transaction.commit();
		session.close();
	}
	
	public static void updateObservacion(ResidenciasObservacion observacion) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.update(observacion);

		transaction.commit();
		session.close();
	}

	public static void insertUniversidad(Universidad universidad) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.save(universidad);

		transaction.commit();
		session.close();
	}
	
	public static void insertEstudiante(Estudiante estudiante) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();

		session.save(estudiante);

		transaction.commit();
		session.close();
	}
	
	public static void insertarEstancia(Estancia estancia) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.save(estancia);
		
		transaction.commit();
		session.close();
	}
	
	public static void actualizarEstancia(Estancia estancia) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.update(estancia);
		
		transaction.commit();
		session.close();
	}
	
	public static void eliminarEstancia(Estancia estancia) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transasction = session.beginTransaction();
		
		session.remove(estancia);
		
		transasction.commit();
		session.close();
	}
}
