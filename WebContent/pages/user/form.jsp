<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.pentalog.nguzun.vo.*"
	import="com.pentalog.nguzun.dao.*"
	import="com.pentalog.nguzun.factory.*" 
	import="java.util.*"
	import="com.pentalog.nguzun.dao.Exception.*"
	import="org.apache.log4j.Logger" %>

<%!
	GroupDAO groupDAO;
	Logger log = Logger.getLogger(UserDAO.class);
%>
<%
	try {
		GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
		Collection<Group> groupList = groupDAO.retrive();
		%>
		<form action="" id="form" onsubmit="saveUser('<%=request.getContextPath()%>/user/update'); return false;">
			<input type="hidden" name="id" id="id">
			<label class="form_label">Name</label>
			<input type="text" name="name" id="name"><br>
			<label class="form_label">Login</label>
			<input type="text" name="login" id="login"><br>
			<label class="form_label">Password</label>
			<input type="password" name="password" id="password"><br>
			<label class="form_label">Group</label>
			<select name="group_id" id="group_id">
				<%for(Group group : groupList) {
				%>
				<option value="<%=group.getId() %>" ><%= group.getName() %></option>
				<% 
				}
				%>
			</select>
			<br>
			<input type="submit" value="Save">
		</form>
		<form method="post" action="<%=request.getContextPath()%>/user/import" id="form-file" enctype="multipart/form-data">
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