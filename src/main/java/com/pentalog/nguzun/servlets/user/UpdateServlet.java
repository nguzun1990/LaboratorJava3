package com.pentalog.nguzun.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.User;

/**
 * Servlet implementation class update
 */
public class UpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(UpdateServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
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
		User user = null;
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		int groupId = Integer.parseInt(request.getParameter("group_id"));
		int id = 0;
		UserDAO dao = DaoFactory.buildObject(UserDAO.class);
		GroupDAO groupDao = DaoFactory.buildObject(GroupDAO.class);
		JSONObject result = new JSONObject();
		try {
			Group group = groupDao.retrive(groupId);
			if (request.getParameter("id").equals("")) {
				user = new User.Builder()
	        		.name(name)
					.login(login)
					.password(password)
					.group(group)
					.build(); 
				id = dao.create(user);
				if (id != 0) {
					success = true;
				}	
			} else {
				id = Integer.parseInt(request.getParameter("id"));
				user = dao.retrive(id);
				if (user != null) {
					user.setName(name);
					user.setLogin(login);
					user.setPassword(password);
					user.setGroup(group);
					success = dao.update(user);
				}		
			}
			result.put("success", success);
			
		} catch (Exception e) {
			result.put("success", false);
			log.error("Update Servlet General Exception: " + e.getMessage(), e);
		}
		out.println(result.toString());
        out.close();
	}

}
