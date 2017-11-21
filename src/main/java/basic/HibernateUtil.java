package basic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import entities.Csv;
import entities.LinkedItem;
import entities.LinkedItemId;
import entities.Priority;
import entities.Project;
import entities.Release;
import entities.ReleaseHistory;
import entities.ReleaseIt;
import entities.ReleaseitHistory;
import entities.Severity;
import entities.Status;
import entities.User;

public class HibernateUtil {
	/*
	 * Hibernate 5 issue with registry
	 * 
	 * https://stackoverflow.com/questions/32405031/hibernate-5-org-hibernate-
	 * mappingexception-unknown-entity
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private synchronized static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", (Exception) ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public synchronized static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public synchronized static List<Csv> readAllCsv() {
		List<Csv> list = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Csv> criteriaQuery = criteriaBuilder.createQuery(Csv.class);
			Root<Csv> root = criteriaQuery.from(Csv.class);
			criteriaQuery.select(root);
			Query<Csv> query = session.createQuery(criteriaQuery);
			list = query.getResultList();
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		} finally {
			session.close();
		}
		return list;
	}

	public synchronized static Project readProjectForName(String name) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Project> cq = builder.createQuery(Project.class);
			Root<Project> root = cq.from(Project.class);
			cq.select(root).where(builder.equal(root.get("nome"), name));
			Query<Project> q = session.createQuery(cq);
			Project p = q.getSingleResult();
			if (p != null)
				return p;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Priority readPriority(float d) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Priority> cq = builder.createQuery(Priority.class);
			Root<Priority> root = cq.from(Priority.class);
			cq.select(root).where(builder.equal(root.get("valore"), d));
			Query<Priority> q = session.createQuery(cq);
			Priority p = q.getSingleResult();
			if (p != null)
				return p;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Severity readSeverity(String name) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Severity> cq = builder.createQuery(Severity.class);
			Root<Severity> root = cq.from(Severity.class);
			cq.select(root).where(builder.equal(root.get("polarion_name"), name));
			Query<Severity> q = session.createQuery(cq);
			Severity s = q.getSingleResult();
			if (s != null)
				return s;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Status readStatus(String name) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Status> cq = builder.createQuery(Status.class);
			Root<Status> root = cq.from(Status.class);
			cq.select(root).where(builder.equal(root.get("polarion_name"), name));
			Query<Status> q = session.createQuery(cq);
			Status s = q.getSingleResult();
			if (s != null)
				return s;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static User readUser(String idPolarion) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> cq = builder.createQuery(User.class);
			Root<User> root = cq.from(User.class);
			cq.select(root).where(builder.equal(root.get("ip_polarion"), idPolarion));
			Query<User> q = session.createQuery(cq);
			User u = q.getSingleResult();
			if (u != null)
				return u;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Release readRelease(String idPolarion) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Release> cq = builder.createQuery(Release.class);
			Root<Release> root = cq.from(Release.class);
			cq.select(root).where(builder.equal(root.get("id_polarion"), idPolarion));
			Query<Release> q = session.createQuery(cq);
			Release r = q.getSingleResult();
			if (r != null)
				return r;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static ReleaseIt readReleaseIT(String idPolarion) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ReleaseIt> cq = builder.createQuery(ReleaseIt.class);
			Root<ReleaseIt> root = cq.from(ReleaseIt.class);
			cq.select(root).where(builder.equal(root.get("id_polarion"), idPolarion));
			Query<ReleaseIt> q = session.createQuery(cq);
			ReleaseIt r = q.getSingleResult();
			if (r != null)
				return r;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static LinkedItem readLinkedItem(LinkedItemId lIId) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			return session.get(LinkedItem.class, lIId);
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static ReleaseHistory readReleaseHistory(Release releaseDiProgetto, Status status,
			Date dataUpdate, User user) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ReleaseHistory> cq = builder.createQuery(ReleaseHistory.class);
			Root<ReleaseHistory> root = cq.from(ReleaseHistory.class);
			cq.select(root)
					.where(builder.and(builder.equal(root.get("cod_id_release"), releaseDiProgetto.getId()),
							builder.equal(root.get("cod_status"), status.getId()),
							builder.equal(root.get("data_update"), dataUpdate),
							builder.equal(root.get("cod_author"), user.getId())));
			Query<ReleaseHistory> q = session.createQuery(cq);
			ReleaseHistory r = q.getSingleResult();
			if (r != null)
				return r;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static ReleaseitHistory readReleaseItHistory(Release releaseDiProgetto, Status status,
			Date dataUpdate, User user) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ReleaseitHistory> cq = builder.createQuery(ReleaseitHistory.class);
			Root<ReleaseitHistory> root = cq.from(ReleaseitHistory.class);
			cq.select(root)
					.where(builder.and(builder.equal(root.get("cod_id_release"), releaseDiProgetto.getId()),
							builder.equal(root.get("cod_status"), status.getId()),
							builder.equal(root.get("data_update"), dataUpdate),
							builder.equal(root.get("cod_author"), user.getId())));
			Query<ReleaseitHistory> q = session.createQuery(cq);
			ReleaseitHistory r = q.getSingleResult();
			if (r != null)
				return r;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	/**
	 * 
	 * Generics methods
	 * 
	 */

	public synchronized static <T> boolean update(Class<T> clazz, Serializable entity) {
		if (entity == null || clazz == null || !clazz.isInstance(entity))
			return false;
		try {
			Session session = getSessionFactory().openSession();
			session.setHibernateFlushMode(FlushMode.ALWAYS);
			session.beginTransaction();
			session.update(entity);
			session.getTransaction().commit();
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return false;
	}

	public synchronized static <T> T loadObject(Class<T> clazz, Serializable key) {
		Session session = getSessionFactory().openSession();
		T dbObject = null;
		try {
			session.beginTransaction();
			dbObject = clazz.cast(session.get(clazz, key));
			session.getTransaction().commit();
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		} finally {
			session.close();
		}
		return dbObject;
	}

	public synchronized static boolean save(Object o) {
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
			session.refresh(o);
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
	public synchronized static boolean rawQuery(String query) {
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