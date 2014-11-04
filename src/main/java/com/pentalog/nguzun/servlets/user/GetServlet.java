package com.pentalog.nguzun.servlets.user;

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

import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.User;

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
		User user = null;
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		JSONObject result = new JSONObject();
		try {
			int id = Integer.parseInt(request.getParameter("id"));		
			UserDAO dao = DaoFactory.buildObject(UserDAO.class);			
			user = dao.retrive(id);
			if (user != null) {
				result.put("success", true);
				result.put("id", user.getId());
				result.put("name", user.getName());
				result.put("login", user.getLogin());
				result.put("password", user.getPassword());
				result.put("group_id", user.getGroup().getId());	        
			} else {
				result.put("success", false);
			}		
			out.println(result.toString());
		} catch (ExceptionDAO e) {
			out.println("{'success':false}");
			log.error("Get Servlet Exception DAO: " + e.getMessage(), e);
		} catch (JSONException e) {
			out.println("{'success':false}");
			log.error("Get Servlet  JSON Exception: " + e.getMessage(), e);
		}		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
