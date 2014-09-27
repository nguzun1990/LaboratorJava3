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
	GroupDAO groupDAO = DaoFactory.buildObject(GroupDAO.class);
	Collection<Group> groupList = groupDAO.retrive();
%>
<fieldset>
	<form method="GET" id="search_form" onsubmit="filterUser('<%=request.getContextPath()%>/pages/user/list.jsp'); return false;" >
		<legend>Search:</legend>
		<label>Name</label>
		<input name="search_name" id="search_name"/>
		<br>
		
		<label>Login</label>
		<input name="search_login" id="search_login"/>
		<br>
		
		<label>Group</label>
		<select name="search_group" id="search_group">
			<option value="" ></option>
			<%for(Group group : groupList) {
			%>
			<option value="<%=group.getId() %>" ><%= group.getName() %></option>
			<% 
			}
			%>
		</select>
		<br>		
		<input type="submit" value="Search">
		<br>
	</form>
<button onclick="applyFiletersSorters('<%=request.getContextPath()%>/pages/user/list.jsp');">Search and orders</button>
</fieldset>



