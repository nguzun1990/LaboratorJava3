package com.pentalog.nguzun.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;

/**
 * Servlet implementation class delete
 */
public class DeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(DeleteServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
    }
    
    public void init() throws ServletException
    {
    }
    

	public void destroy() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean success = false;
		long id = 0;
		UserDAO dao = DaoFactory.buildObject(UserDAO.class);
		JSONObject result = new JSONObject();
		try {
			if (!request.getParameter("id").equals("")) {
				id = Long.parseLong(request.getParameter("id"));
				success = dao.delete(id);	
			}
			result.put("success", success);
		} catch (ExceptionDAO e) {
			log.error("Update Servlet Exception DAO: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error("Update Servlet General Exception: " + e.getMessage(), e);
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(result.toString());
        out.close();
	}

}
