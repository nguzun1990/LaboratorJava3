<form method="GET" id="order_form" onsubmit="sortUser('<%=request.getContextPath()%>/user/list'); return false;" >
	<fieldset>
	    <legend>Search:</legend>
		<label>Sort by</label>
		<select name="orderBy" id="orderBy">
			<option value="name">Name</option>
			<option value="login">Login</option>
			<option value="group">Group</option>
		</select>
		<label>direction: </label>
		<select name="direction" id="direction">
			<option value="asc">Ascending</option>
			<option value="desc">Descending</option>
		</select>
		<input type="submit" value="Sort">
	</fieldset>
</form>


