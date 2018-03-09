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
<script type="text/javascript">
	$(document).ready(function() {

		console.log("Authenicate");

	});

	function onSubmit() {

		var email = $('#email').val();
		var passwd = $('#password').val();
		var url = '<%=request.getParameter("accessUrl")%>';
		var pass = '<%=request.getParameter("initUrl")%>';
		console.log(pass);
		var xhr = $.ajax({
			type : "POST",
			url  :  url,
			data : {
				email : email,
				password : passwd,
				passUrl : pass,
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
	<form name="Authenticateform">
		<input type="text" id="email" placeholder="Email ID" />
		<p />
		<input type="password" id="password" placeholder="Password"> <input
			type="button" onclick="onSubmit()" value="Authenicate" />
	</form>
	<strong>Authentication Error Response</strong>:
	<div id="ErrorReponse"></div>
</body>
</html>