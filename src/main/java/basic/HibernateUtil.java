package basic;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	/*
	 * Hibernate 5 issue with registry
	 * 
	 * https://stackoverflow.com/questions/32405031/hibernate-5-org-hibernate-
	 * mappingexception-unknown-entity
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static boolean save(Object o) {
		Session session = null;
		try {
			if (!(o instanceof Serializable)) {
				ClassNotFoundException ex = new ClassNotFoundException("Object o is not instance of Serializable");
				throw ex;
			}
			session = sessionFactory.openSession();
			session.setHibernateFlushMode(FlushMode.ALWAYS);
			Transaction tx = session.beginTransaction();
			session.save(o);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean rawQuery(String query) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.createSQLQuery(query).executeUpdate();
			return true;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}
}