<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.pentalog.nguzun.vo.*"
	import="com.pentalog.nguzun.dao.*"
	import="com.pentalog.nguzun.factory.*" 
	import="java.util.*"
	import="com.pentalog.nguzun.dao.Exception.*"
	import="org.apache.log4j.Logger" %>
<%!
	UserDAO userDAO;
	GroupDAO groupDAO;
	Logger log = Logger.getLogger(UserDAO.class);
%>
<%
	
	try{
		userDAO = DaoFactory.buildObject(UserDAO.class);
		GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
		Collection<User> userList = userDAO.retrive();
		%>
		<input type="button" value="Add" onclick="showAddUserForm()">
		<table border="1">
			<tr>
				<th>Name</th>
				<th>Login</th>
				<th>Password</th>
				<th>Group</th>
				<th>Operations</th>
			</tr>
			<%
				for(User user : userList) {
			%>
			<tr>
				<td><%= user.getName() %></td>
				<td><%= user.getLogin() %></td>
				<td><%= user.getPassword() %></td>
				<td><%= user.getGroup().getName() %></td>
				<td>
					<input type="button" value="Edit" onclick="showEditUserForm(<%= user.getId() %>, '<%=request.getContextPath() %>/user/get?id=<%= user.getId() %>')">
					<input type="button" value="Delete" onclick="deleteUser(<%= user.getId() %>, '<%=request.getContextPath() %>/user/delete')">
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<br>
		<a href="<%=request.getContextPath() %>/user/export?filetype=csv">Export list to CSV File</a>
		<a href="<%=request.getContextPath() %>/user/export?filetype=xml">Export list to XML File</a>
		<%
	} catch (ExceptionDAO e) {
		log.error("list.jsp: an error dao was occured: " + e.getMessage(), e);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	
%>

