<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"
	type="text/javascript"></script>
<script src="https://apis.google.com/js/platform.js?onload=init" async
	defer></script>
<meta name="google-signin-client_id"
	content="651385506701-pttbbk9tmt921c7qv6t97ve4pgo2thph.apps.googleusercontent.com">	
<script type="text/javascript">
	$(document).ready(function() {

		console.log("Authenicate");

	});

	function onSubmit(googleUser) {

		var email = $('#email').val();
		var passwd = $('#password').val();
		var url = '<%=request.getParameter("accessUrl")%>';
		var pass = '<%=request.getParameter("initUrl")%>';
		console.log(pass);
		
		var profile = googleUser.getBasicProfile();
		var profileEmail = profile.getEmail();
		var id_token = googleUser.getAuthResponse().id_token;
		
		var xhr = $.ajax({
			type : "POST",
			url  :  url,
			data : {
				email : profileEmail,
				password : "",
				passUrl : pass,
				authType:"EXT",
				authSource:"GOOGLE",
				idtoken:id_token
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader('x-request-type', 'AUTHENTICATE');
			},
			success : function(data) {
				var cookie = xhr.getResponseHeader('x-bearer-token');
				window.location = pass+'?cookie='+cookie;
							
							
			},
			error: function(data){
					console.log(data);
					$('#ErrorReponse').html(data.responseText);
			},

		});

	};
</script>
</head>
<body>
	<div class="g-signin2" data-onsuccess="onSubmit" id="loginDiv"></div>
</body>
</html>