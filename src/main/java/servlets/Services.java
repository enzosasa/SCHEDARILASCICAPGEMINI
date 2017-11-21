package servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import basic.Scheduler;

public class Services extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static LinkedList<Scheduler> schedulersList = new LinkedList<Scheduler>();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null)
			switch (action) {
			case "create":
				Scheduler s = null;
				switch (request.getParameter("mode")) {
				case "truncate":
					s = new Scheduler(Scheduler.ONE_SHOT, Scheduler.TRUNCATE_DATA, 7200000, "USER_1");
					break;
				case "append":
					s = new Scheduler(Scheduler.LOOP_ON_DELAY, Scheduler.APPEND_DATA, 7200000, "USER_1");
					break;
				case "update":
					s = new Scheduler(Scheduler.LOOP_ON_DELAY, Scheduler.UPDATE_DATA, 7200000, "USER_1");
					break;
				}

				s.start();
				schedulersList.add(s);
				String msgCreate = "Scheduler " + s.getUniqueID() + " has been started.";
				Logger.getLogger(Services.class.getName()).log(Level.INFO, msgCreate);
				response.sendRedirect("index.jsp?msg=" + msgCreate);
				break;
			case "destroy":
				String msgDestroy = "Attenzione, nessuno scheduler avviato.";
				if (!schedulersList.isEmpty()) {
					String uniqueID = schedulersList.getFirst().getUniqueID();
					schedulersList.getFirst().stopScheduler();
					schedulersList.removeFirst();
					msgDestroy = "Scheduler " + uniqueID + " has been stopped.";
				}
				Logger.getLogger(Services.class.getName()).log(Level.INFO, msgDestroy);
				response.sendRedirect("index.jsp?msg=" + msgDestroy);
				break;
			default:
				Logger.getLogger(Services.class.getName()).log(Level.WARNING, "Action is empty or invalid.");
				break;
			}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
