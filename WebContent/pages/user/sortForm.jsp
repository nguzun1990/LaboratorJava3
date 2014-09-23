<form method="GET" action="<%=request.getContextPath()%>/pages/user/list.jsp" >
	<label>Sort by</label>
	<select name="orderBy">
		<option value="name">Name</option>
		<option value="login">Login</option>
		<option value="group.name">Group</option>
	</select>
	<label>direction: </label>
	<select name="direction">
		<option value="asc">Ascending</option>
		<option value="desc">Descending</option>
	</select>
	<input type="submit" value="Sort">
</form>


