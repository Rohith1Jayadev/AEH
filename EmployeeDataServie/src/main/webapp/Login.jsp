<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"
	type="text/javascript"></script>
<script src="js/myapp.js" type="text/javascript"></script>
<title>Login Page</title>
</head>
<body>
	<p>Welcome to the Login Page</p>

	<form>
		<p>
			Enter Your Name: <input type="text" id="userName" />
			<input type="hidden" id="cookie" value =<%=request.getParameter("cookie")%> />
			<p>
			<span style="display: inline-block; width: 10;"></span> <input
				type="button" id="access" value="Access First Resource" /> <span
				style="display: inline-block; width: 10;"></span>
				<input
				type="button" id="access2" value="Access Second Resource" />
				<span
				style="display: inline-block; width: 10;"></span>
				<input
				type="button" id="logout" value="LogOut" /> 
	</form>
	<br>
	<br>
	<strong>Ajax Response</strong>:
	<div id="ajaxGetUserServletResponse"></div>
</body>
</html>