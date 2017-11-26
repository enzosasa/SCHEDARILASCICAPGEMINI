package servlets;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.Criteria;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

import basic.HibernateUtil;
import entities.Csv;
import entities.ReleaseIt;
import entities.User;

@Path("/")
public class ServicesGet {
	
	private Session session;

	@GET
	@Path("/csvForIdPolarion")
	@Produces("application/json")
	public Response getCsvForIdPolarion(@QueryParam("idPolarion") String idPolarion) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria cr = session.createCriteria(Csv.class);
		cr.add(Restrictions.eq("idPolarion", idPolarion));
		List<Csv> csv = cr.list();
		return Response.accepted().entity(csv).build();
//		return csv;
	}
	
	@GET
	@Path("/releaseIT")
	@Produces("application/json")
	public Response getReleaseIt() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query query = session.createQuery("SELECT COUNT(*),YEAR(data_creazione) FROM ReleaseIt GROUP BY YEAR(data_creazione)");
		List<Gson> result = query.getResultList();

		session.getTransaction().commit();
		session.close();
		
		return Response.accepted().entity(result).build();

	}
	
	@GET
	@Path("/allReleaseIT")
	@Produces("application/json")
	public Response getAllReleaseIt() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query query = session.createQuery("SELECT COUNT(*),YEAR(data_creazione) FROM ReleaseIt GROUP BY YEAR(data_creazione)");
		List<Gson> result = query.getResultList();

		session.getTransaction().commit();
		session.close();
		
		return Response.accepted().entity(result).build();

	}
	
	
	

}
