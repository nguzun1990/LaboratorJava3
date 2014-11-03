<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="button" value="Add" onclick="showAddUserForm()">
<table border="1">
	<tr>
		<th>Name</th>
		<th>Login</th>
		<th>Password</th>
		<th>Group</th>
		<th>Operations</th>
	</tr>
	<c:forEach items="${requestScope.userList}" var="user">
	<c:set var="userId" value="${user.getName()}" scope="request"></c:set>
	<tr>
		<td><c:out value="${user.getName()}"></c:out></td>
		<td><c:out value="${user.getLogin()}"></c:out></td>
		<td><c:out value="${user.getPassword()}"></c:out></td>
		<td><c:out value="${user.getGroup().getName()}"></c:out></td>
		<td><input type="button" value="Edit"
			onclick="showEditUserForm(<c:out value="${requestScope.userId }" ></c:out>, '<%=request.getContextPath()%>/user/get?id=<c:out value="${requestScope.userId }" ></c:out>')">
			<input type="button" value="Delete"
			onclick="deleteUser(<c:out value="${requestScope.userId }" ></c:out>, '<%=request.getContextPath()%>/user/delete')">
		</td>
	</tr>
	</c:forEach>
</table>

<br>
<a href="<%=request.getContextPath()%>/user/export?filetype=csv">Export	list to CSV File</a>
<a href="<%=request.getContextPath()%>/user/export?filetype=xml">Export	list to XML File</a>


