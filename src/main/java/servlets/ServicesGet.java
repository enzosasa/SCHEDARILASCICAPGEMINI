package servlets;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import basic.HibernateUtil;
import entities.Csv;

@Path("/")
public class ServicesGet {
	
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

}
