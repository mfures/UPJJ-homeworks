<%@page import="hr.fer.zemris.java.hw15.model.BlogUser"%>
<%@page import="hr.fer.zemris.java.hw15.model.RegistrationForm"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	RegistrationForm form = (RegistrationForm) request.getAttribute("form");
	Object buo = session.getAttribute("logedUser");
	String output = "";
	if (buo == null) {
		output = "<p>not logged in</p>";
	} else {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>");
		BlogUser bu = (BlogUser) buo;
		sb.append(bu.getFirstName());
		sb.append(" ");
		sb.append(bu.getLastName());
		sb.append("  <a href=\"");
		sb.append(request.getContextPath() + "/servleti/logout\">Log out</a></p>");
		output = sb.toString();
	}
%>

<!DOCTYPE html>
<html>
<head>
<title>Main</title>
</head>

<body>
	<%=output%>
	<h1>Login</h1>
	<%
		if (form.getErrors().containsKey("password") || form.getErrors().containsKey("nick")) {
	%>
	<p>Invalid user name or password.</p>
	<%
		}
	%>
	<form action="${pageContext.request.contextPath}/servleti/main"
		method="post">
		Nick: <input type="text" name="nick" value="${form.nick}"> <br>
		Password: <input type="password" name="password"> <input
			type="submit" value="Login">
	</form>

	<p>
		<a href="register">Don't have an account?</a>
	</p>

	<p>Registered authors</p>
	<ul>
		<c:forEach items="${users}" var="users">
			<li><a href="author/${users.nick}">${users.firstName}<%=" "%>${users.lastName}</a></li>
		</c:forEach>
	</ul>
</body>
</html>