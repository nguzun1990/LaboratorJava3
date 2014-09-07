<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.pentalog.nguzun.vo.*"
	import="com.pentalog.nguzun.dao.*"
	import="com.pentalog.nguzun.factory.*" 
	import="java.util.*"
	import="com.pentalog.nguzun.dao.Exception.*"
	import="org.apache.log4j.Logger" %>
<%!
	GroupDAO groupDAO;
	Logger log = Logger.getLogger(GroupDAO.class);
%>
<%
	
	try{
		groupDAO = DaoFactory.buildObject(GroupDAO.class);
		Collection<Group> groupList = groupDAO.retrive();
		%>
		<input type="button" value="Add" onclick="showAddGroupForm()">
		<table border="1">
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Role</th>
				<th>Operations</th>
			</tr>
			<%
				for(Group group : groupList) {
			%>
			<tr>
				<td><%= group.getName() %></td>
				<td><%= group.getDescription() %></td>
				<td><%= group.getRole().getName() %></td>
				<td>
					<input type="button" value="Edit" onclick="showEditGroupForm(<%= group.getId() %>, '<%=request.getContextPath() %>/group/get?id=<%= group.getId() %>')">
					<input type="button" value="Delete" onclick="deleteGroup(<%= group.getId() %>, '<%=request.getContextPath() %>/group/delete')">
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<br>
		<a href="<%=request.getContextPath() %>/group/export?filetype=csv">Export list to CSV File</a>
		<a href="<%=request.getContextPath() %>/group/export?filetype=xml">Export list to XML File</a>
		<%
	} catch (ExceptionDAO e) {
		log.error("list.jsp: an error dao was occured: " + e.getMessage(), e);
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
	
%>

