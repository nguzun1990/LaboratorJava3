package com.pentalog.nguzun.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;


import com.pentalog.nguzun.dao.UserDAO;
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
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Boolean success = false;
		int id = 0;
		UserDAO dao = DaoFactory.buildObject(UserDAO.class);
		JSONObject result = new JSONObject();
		try {
			if (!request.getParameter("id").equals("")) {
				id = Integer.parseInt(request.getParameter("id"));
				success = dao.delete(id);	
			}
			result.put("success", success);			
		} catch (Exception e) {
			result.put("success", false);
			log.error("Delete Servlet Exception: " + e.getMessage(), e);
		}
		out.println(result.toString());
        out.close();
	}

}
