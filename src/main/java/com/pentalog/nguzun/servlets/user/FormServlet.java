package com.pentalog.nguzun.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.pentalog.nguzun.common.DependencyParams;
import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.UserDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.User;

/**
 * Servlet implementation class get
 */
public class FormServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(FormServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormServlet() {
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
		Logger log = Logger.getLogger(UserDAO.class);
		GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
		Collection<Group> groupList;
		try {
			groupList = groupDAO.retrive();
			request.setAttribute("groupList", groupList);
			request.setAttribute("success", true);
		} catch (ExceptionDAO e) {
			request.setAttribute("success", false);
			log.error("List Servlet Exception: " + e.getMessage(), e);
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/user/form.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
