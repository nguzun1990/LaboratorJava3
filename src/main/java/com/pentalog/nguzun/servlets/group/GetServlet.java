package com.pentalog.nguzun.servlets.group;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;

/**
 * Servlet implementation class get
 */
public class GetServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(GetServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetServlet() {
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
		Group group = null;
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		JSONObject result = new JSONObject();
		try {
			long id = Long.parseLong(request.getParameter("id"));		
			GroupDAO dao = DaoFactory.buildObject(GroupDAO.class);			
			group = dao.retrive(id);
			if (group != null) {
				result.put("success", true);
				result.put("id", group.getId());
				result.put("name", group.getName());
				result.put("description", group.getDescription());
				result.put("role_id", group.getIdRole());	        
			} else {
				result.put("success", false);
			}		
		} catch (ExceptionDAO e) {
			log.error("Get Servlet Exception DAO: " + e.getMessage(), e);
		} catch (JSONException e) {
			log.error("Get Servlet  JSON Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error("Get Servlet General Exception: " + e.getMessage(), e);
		}
		out.println(result.toString());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
