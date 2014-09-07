<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.pentalog.nguzun.vo.*"
	import="com.pentalog.nguzun.dao.*"
	import="com.pentalog.nguzun.factory.*" 
	import="java.util.*"
	import="com.pentalog.nguzun.dao.Exception.*"
	import="org.apache.log4j.Logger" %>

<%!
	RoleDAO roleDAO;
	Logger log = Logger.getLogger(GroupDAO.class);
%>
<%
	try {
		roleDAO = DaoFactory.buildObject(RoleDAO.class);
		Collection<Role> roleList = roleDAO.retrive();
		%>
		<form action="" id="form" onsubmit="saveGroup('<%=request.getContextPath()%>/group/update'); return false;">
			<input type="hidden" name="id" id="id">
			<label class="form_label">Name</label>
			<input type="text" name="name" id="name"><br>
			<label class="form_label">Description</label>
			<input type="text" name="description" id="description"><br>
			<label class="form_label">Role</label>
			<select name="role_id" id="role_id">
				<%for(Role role : roleList) {
				%>
				<option value="<%=role.getId() %>" ><%= role.getName() %></option>
				<% 
				}
				%>
			</select>
			<br>
			<input type="submit" value="Save">
		</form>
		<br/>
		<form method="post" action="<%=request.getContextPath()%>/group/import" id="form-file" enctype="multipart/form-data">
			<input type="file" name="import_file">
			<br/>
			<input type="submit" value="Import">
		</form>
		<%
	} catch (ExceptionDAO e) {
		log.error("get: an error dao was occured: " + e.getMessage(), e);
		%><span class="error">An database error occured<span/><%
	} catch (Exception e) {
		log.error(e.getMessage(), e);
		%><span class="error">An server error occured<span/><%
	}
%>