<form method="GET" id="order_form" onsubmit="sortUser('<%=request.getContextPath()%>/pages/user/list.jsp'); return false;" >
	<label>Sort by</label>
	<select name="orderBy" id="orderBy">
		<option value="name">Name</option>
		<option value="login">Login</option>
		<option value="group.name">Group</option>
	</select>
	<label>direction: </label>
	<select name="direction" id="direction">
		<option value="asc">Ascending</option>
		<option value="desc">Descending</option>
	</select>
	<input type="submit" value="Sort">
</form>


