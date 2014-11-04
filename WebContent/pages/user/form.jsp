<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
				<c:forEach items="${requestScope.groupList}" var="group">
					<option value="<c:out value="${group.getId()}"></c:out>" ><c:out value="${group.getName()}"></c:out></option>
				</c:forEach>
			</select>
			<br>
			<input type="submit" value="Save">
		</form>
		<div id="output"></div>
		<form method="post" action="<%=request.getContextPath()%>/user/import" id="form-file" enctype="multipart/form-data">
			<input type="file" name="import_file" id="import_file">
			<br/>
			<input type="submit" value="Import">
		</form>