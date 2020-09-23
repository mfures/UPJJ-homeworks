<%@page import="hr.fer.zemris.java.hw13.servleti.GlasanjeServlet.Entry"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%! @SuppressWarnings("unchecked") %>


<%
	String color = "FFFFFF";
	Object bgcol = session.getAttribute("pickedBgCol");
	if (bgcol != null) {
		color = (String) bgcol;
	}

	Map<Integer, Entry> entries = (Map<Integer, Entry>)request.getAttribute("entries");
%>

<!DOCTYPE html>
<html>
<head>
<title>Glasaj za bend</title>
</head>

<body style="background-color:#<%=color%>;">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
	<ol>
	<%for(Map.Entry<Integer,Entry> entry:entries.entrySet()) {%>
		<li><a href="glasanje-glasaj?id=<%=entry.getKey()%>"><%=entry.getValue().getName() %></a></li>
		
	<%}%>
	</ol>
 </body>
</html>