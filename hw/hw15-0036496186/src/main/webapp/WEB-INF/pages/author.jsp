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
<title>Author</title>
</head>

<body>
	<%=output%>
	<h1>Author</h1>
	<ul>
		<c:forEach items="${blogEntries}" var="entry">
			<li><a
				href="<%=request.getContextPath()%>/servleti/author/${author.nick}/${entry.id}">${entry.title}</a></li>
		</c:forEach>
	</ul>

	<c:if test="${editable}">
		<a
			href="${pageContext.request.contextPath}/servleti/author/${author.nick}/new">New</a>
	</c:if>
</body>
</html>