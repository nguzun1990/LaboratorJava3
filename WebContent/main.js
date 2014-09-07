function showEditUserForm(id, url) {
	$.ajax({
	  type: "GET",
	  url: url,
	  data: {
		  'id': id
	  }
	})
	.done(function(data) {
		if (data.success === true) {
			$('#form #id').val(data.id);
			$('#form #name').val(data.name);
			$('#form #login').val(data.login);
			$('#form #password').val(data.password);
			$('#form #group_id').val(data.group_id);
		} else {
			clearUserForm();
		}
		
	})
}

function showAddUserForm() {
	clearUserForm();
}

function deleteUser(id, url) {
	var answer = confirm("Do you want to delete user ?");
	if (answer == true) {
		$.ajax({
			  type: "POST",
			  url: url,
			  data: {
				  'id': id
			  }
			})
			.done(function(data) {
				if (data.success === true) {
					showSuccessMsg('The user was deleted');
					clearUserForm();
				} else {
					showErrorMsg('The user wasn\'t deleted');
				}
				reloadUserList();
			});
	} 	
}


function saveUser(url) {
	if (validateUserForm()) {
		$.ajax({
		  type: "POST",
		  url: url,
		  data: {
			  'id': $('#form #id').val(),
			  'name': $('#form #name').val(),
			  'login': $('#form #login').val(),
			  'password': $('#form #password').val(),
			  'group_id': $('#form #group_id').val(),
		  }
		})
		.done(function(data) {
			if (data.success === true) {
				showSuccessMsg('The user was saved');
				clearUserForm();
			} else {
				showErrorMsg('The user wasn\'t saved');
			}
			reloadUserList();
		});
	} else {
		showErrorMsg("Please verify the fields !")
	}
	
}

function reloadUserList() {
	$.ajax({
		  type: "GET",
		  url: reloadUserUrl,
		})
		.done(function(data) {
			$('.left').html(data);			
		})
}


function clearUserForm() {
	$('#form #id').val("");
	$('#form #name').val("");
	$('#form #login').val("");
	$('#form #password').val("");
	$('#form #group_id').val("");
}


function showSuccessMsg(message) {
		$('#message').html(message);
		$('#message').removeClass( "fail" );
		$('#message').addClass( "success" );
}

function showErrorMsg(message) {
		$('#message').html(message);
		$('#message').addClass( "fail" );
		$('#message').removeClass( "success" );
}

function validateUserForm() {
	var isValid = true;
	isValid = validateInput($('#form #name')) && isValid;
	isValid = validateInput($('#form #login')) && isValid;
	isValid = validateInput($('#form #password')) && isValid;
	isValid = validateInput($('#form #group_id')) && isValid;
	
	return isValid;
}

function validateInput(input) {
	if ((input.val() == "") || (input.val() == null)){
		input.addClass('error_input');
		return false;
	} else {
		input.removeClass('error_input');
		return true;
	}
}


function showEditGroupForm(id, url) {
	$.ajax({
	  type: "GET",
	  url: url,
	  data: {
		  'id': id
	  }
	})
	.done(function(data) {
		if (data.success === true) {
			$('#form #id').val(data.id);
			$('#form #name').val(data.name);
			$('#form #description').val(data.description);
			$('#form #role_id').val(data.role_id);
		} else {
			clearUserForm();
		}
		
	})
}

function showAddGroupForm() {
	clearGroupForm();
}

function deleteGroup(id, url) {
	var answer = confirm("Do you want to delete group ?");
	if (answer == true) {
		$.ajax({
			  type: "POST",
			  url: url,
			  data: {
				  'id': id
			  }
			})
			.done(function(data) {
				if (data.success === true) {
					showSuccessMsg('The group was deleted');
					clearUserForm();
				} else {
					showErrorMsg('The group wasn\'t deleted');
				}
				reloadGroupList();
			});
	} 	
}


function saveGroup(url) {
	if (validateGroupForm()) {
		$.ajax({
		  type: "POST",
		  url: url,
		  data: {
			  'id': $('#form #id').val(),
			  'name': $('#form #name').val(),
			  'description': $('#form #description').val(),
			  'role_id': $('#form #role_id').val(),
		  }
		})
		.done(function(data) {
			if (data.success === true) {
				showSuccessMsg('The group was saved');
				clearGroupForm();
			} else {
				showErrorMsg('The group wasn\'t saved');
			}
			reloadGroupList();
		});
	} else {
		showErrorMsg("Please verify the fields !")
	}
	
}

function reloadGroupList() {
	$.ajax({
		  type: "GET",
		  url: reloadGroupUrl,
		})
		.done(function(data) {
			$('.left').html(data);			
		})
}


function clearGroupForm() {
	$('#form #id').val("");
	$('#form #name').val("");
	$('#form #description').val("");
	$('#form #role_id').val("");
}

function validateGroupForm() {
	var isValid = true;
	isValid = validateInput($('#form #name')) && isValid;
	isValid = validateInput($('#form #description')) && isValid;
	isValid = validateInput($('#form #role_id')) && isValid;
	
	return isValid;
}