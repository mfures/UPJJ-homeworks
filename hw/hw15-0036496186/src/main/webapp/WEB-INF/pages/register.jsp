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
<title>Register</title>
</head>

<body>
	<%=output %>
	<h1>Register</h1>
	<form action="${pageContext.request.contextPath}/servleti/register"
		method="post">
		First Name:<input type="text" name="firstName"
			value="${form.firstName}">
		<%
			if (form.getErrors().containsKey("firstName")) {
		%>
		<%=form.getErrors().get("firstName")%>
		<%
			}
		%><br> Last Name: <input type="text" name="lastName"
			value="${form.lastName}">
		<%
			if (form.getErrors().containsKey("lastName")) {
		%>
		<%=form.getErrors().get("lastName")%>
		<%
			}
		%><br> Email: <input type="text" name="email"
			value="${form.email}">
		<%
			if (form.getErrors().containsKey("email")) {
		%>
		<%=form.getErrors().get("email")%>
		<%
			}
		%><br> Nick: <input type="text" name="nick" value="${form.nick}">
		<%
			if (form.getErrors().containsKey("nick")) {
		%>
		<%=form.getErrors().get("nick")%>
		<%
			}
		%><br> Password: <input type="password" name="password">
		<%
			if (form.getErrors().containsKey("password")) {
		%>
		<%=form.getErrors().get("password")%>
		<%
			}
		%>
		<input type="submit" value="Register">
	</form>
</body>
</html>