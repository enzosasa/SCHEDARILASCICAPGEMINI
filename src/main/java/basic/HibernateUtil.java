package basic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Csv> criteriaQuery = criteriaBuilder.createQuery(Csv.class);
			Root<Csv> root = criteriaQuery.from(Csv.class);

			// Query
			criteriaQuery.select(root);

			Query<Csv> query = session.createQuery(criteriaQuery);
			list = query.getResultList();

			session.getTransaction().commit();
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return list;
	}

	public synchronized static Project readProjectForName(String name) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Project> cq = builder.createQuery(Project.class);
			Root<Project> root = cq.from(Project.class);

			// Query
			cq.select(root).where(builder.equal(root.get("nome"), name));

			Query<Project> q = session.createQuery(cq);
			Project p = q.getSingleResult();

			session.getTransaction().commit();

			if (p != null)
				return p;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Priority readPriority(float d) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Priority> cq = builder.createQuery(Priority.class);
			Root<Priority> root = cq.from(Priority.class);

			// Query
			cq.select(root).where(builder.equal(root.get("valore"), d));

			Query<Priority> q = session.createQuery(cq);
			Priority p = q.getSingleResult();

			session.getTransaction().commit();
			if (p != null)
				return p;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Severity readSeverity(String name) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Severity> cq = builder.createQuery(Severity.class);
			Root<Severity> root = cq.from(Severity.class);

			// Query
			cq.select(root).where(builder.equal(root.get("polarionName"), name));

			Query<Severity> q = session.createQuery(cq);
			Severity s = q.getSingleResult();

			session.getTransaction().commit();
			if (s != null)
				return s;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Status readStatus(String name) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Status> cq = builder.createQuery(Status.class);
			Root<Status> root = cq.from(Status.class);

			// Query
			cq.select(root).where(builder.equal(root.get("polarionName"), name));

			Query<Status> q = session.createQuery(cq);
			Status s = q.getSingleResult();

			session.getTransaction().commit();
			if (s != null)
				return s;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Csv readCsvByIdPolarion(String idPolarion) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Csv> cq = builder.createQuery(Csv.class);
			Root<Csv> root = cq.from(Csv.class);

			// Query
			cq.select(root).where(builder.equal(root.get("idPolarion"), idPolarion));

			Query<Csv> q = session.createQuery(cq);
			Csv csv = q.getSingleResult();

			session.getTransaction().commit();
			if (csv != null)
				return csv;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static User readUser(String idPolarion) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> cq = builder.createQuery(User.class);
			Root<User> root = cq.from(User.class);

			// Query
			cq.select(root).where(builder.equal(root.get("idPolarion"), idPolarion));

			Query<User> q = session.createQuery(cq);
			User u = q.getSingleResult();

			session.getTransaction().commit();
			if (u != null)
				return u;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static Release readRelease(String idPolarion) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Release> cq = builder.createQuery(Release.class);
			Root<Release> root = cq.from(Release.class);

			// Query
			cq.select(root).where(builder.equal(root.get("idPolarion"), idPolarion));

			Query<Release> q = session.createQuery(cq);
			Release r = q.getSingleResult();

			session.getTransaction().commit();
			if (r != null)
				return r;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static ReleaseIt readReleaseIT(String idPolarion) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ReleaseIt> cq = builder.createQuery(ReleaseIt.class);
			Root<ReleaseIt> root = cq.from(ReleaseIt.class);

			// Query
			cq.select(root).where(builder.equal(root.get("idPolarion"), idPolarion));

			Query<ReleaseIt> q = session.createQuery(cq);
			ReleaseIt r = q.getSingleResult();

			session.getTransaction().commit();
			if (r != null)
				return r;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static LinkedItem readLinkedItem(LinkedItemId lIId) {
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			// Query
			LinkedItem li = session.get(LinkedItem.class, lIId);

			session.getTransaction().commit();
			return li;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static ReleaseHistory readReleaseHistory(Release release, Status status, Date dataUpdate,
			User user) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ReleaseHistory> cq = builder.createQuery(ReleaseHistory.class);
			Root<ReleaseHistory> root = cq.from(ReleaseHistory.class);

			// Query
			if (status != null && dataUpdate != null && user != null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status), builder.equal(root.get("dataUpdate"), dataUpdate),
						builder.equal(root.get("user"), user.getId())));
			else if (status != null && dataUpdate != null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status), builder.equal(root.get("dataUpdate"), dataUpdate)));
			else if (status != null && dataUpdate == null && user != null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status), builder.equal(root.get("user"), user.getId())));
			else if (status == null && dataUpdate != null && user != null)
				cq.select(root)
						.where(builder.and(builder.equal(root.get("release"), release),
								builder.equal(root.get("dataUpdate"), dataUpdate),
								builder.equal(root.get("user"), user.getId())));
			else if (status != null && dataUpdate == null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status)));
			else if (status == null && dataUpdate != null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("dataUpdate"), dataUpdate)));
			else if (status == null && dataUpdate == null && user != null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("user"), user.getId())));
			else if (status == null && dataUpdate == null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release)));

			Query<ReleaseHistory> q = session.createQuery(cq);
			ReleaseHistory r = q.getSingleResult();

			session.getTransaction().commit();
			if (r != null)
				return r;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return null;
	}

	public synchronized static ReleaseitHistory readReleaseItHistory(ReleaseIt release, Status status, Date dataUpdate,
			User user) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ReleaseitHistory> cq = builder.createQuery(ReleaseitHistory.class);
			Root<ReleaseitHistory> root = cq.from(ReleaseitHistory.class);

			// Query
			if (status != null && dataUpdate != null && user != null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status), builder.equal(root.get("dataUpdate"), dataUpdate),
						builder.equal(root.get("user"), user.getId())));
			else if (status != null && dataUpdate != null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status), builder.equal(root.get("dataUpdate"), dataUpdate)));
			else if (status != null && dataUpdate == null && user != null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status), builder.equal(root.get("user"), user.getId())));
			else if (status == null && dataUpdate != null && user != null)
				cq.select(root)
						.where(builder.and(builder.equal(root.get("release"), release),
								builder.equal(root.get("dataUpdate"), dataUpdate),
								builder.equal(root.get("user"), user.getId())));
			else if (status != null && dataUpdate == null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("status"), status)));
			else if (status == null && dataUpdate != null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("dataUpdate"), dataUpdate)));
			else if (status == null && dataUpdate == null && user != null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release),
						builder.equal(root.get("user"), user.getId())));
			else if (status == null && dataUpdate == null && user == null)
				cq.select(root).where(builder.and(builder.equal(root.get("release"), release)));

			Query<ReleaseitHistory> q = session.createQuery(cq);
			ReleaseitHistory r = q.getSingleResult();

			session.getTransaction().commit();
			if (r != null)
				return r;
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException e) {
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
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();
			// session.setHibernateFlushMode(FlushMode.ALWAYS);

			// Query
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
		T dbObject = null;
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();
			dbObject = clazz.cast(session.get(clazz, key));
			session.getTransaction().commit();
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
		}
		return dbObject;
	}

	public synchronized static boolean save(Object o) {
		try {
			if (!(o instanceof Serializable)) {
				ClassNotFoundException ex = new ClassNotFoundException("Object o is not instance of Serializable");
				throw ex;
			}
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();
			// session.setHibernateFlushMode(FlushMode.ALWAYS);

			// Query
			session.save(o);

			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
			return false;
		}
	}

	public synchronized static boolean rawQuery(String query) {
		try {
			Session session = getSessionFactory().getCurrentSession();
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			// Query
			session.createQuery(query).executeUpdate();

			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("HibernateUtil.java throws exception", e);
			Logger.getLogger(HibernateUtil.class.getName()).log(Level.SEVERE, "HibernateUtil.java throws exception", e);
			return false;
		}
	}
}