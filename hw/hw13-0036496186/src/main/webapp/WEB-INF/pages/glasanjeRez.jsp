<%@page import="java.util.ArrayList"%>
<%@page
	import="hr.fer.zemris.java.hw13.servleti.GlasanjeRezultatiServlet.Result"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.hw13.servleti.GlasanjeServlet.Entry"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%!@SuppressWarnings("unchecked")%>


<%
	String color = "FFFFFF";
	Object bgcol = session.getAttribute("pickedBgCol");
	if (bgcol != null) {
		color = (String) bgcol;
	}

	Object res = request.getAttribute("results");
	List<Result> results;
	if (res == null) {
		results = new ArrayList<>();
	} else {
		results = (List<Result>) res;
	}
	int max = 0;
	if (results.size() != 0) {
		max = results.get(0).getVotes();
	}
%>

<!DOCTYPE html>
<html>
<head>
<title>Glasaj za bend</title>
</head>

<body style="background-color:#<%=color%>;">
	<h1>Rezultati glasanj</h1>
	<p>Ovo su rezultati glasanja:</p>
	<table border="1">
		<tr>
			<th>Bend</th>
			<th>Broj glasova</th>
		</tr>
		<%
			for (Result result : results) {
		%>
		<tr>
			<td><%=result.getEntry().getName()%></td>
			<td><%=result.getVotes()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
	<%for(int i=0;i<results.size();i++){
		if(results.get(i).getVotes()<max) break;%>
		<li><a href="<%=results.get(i).getEntry().getLink() %>" target="_blank"><%=results.get(i).getEntry().getName()%></a></li>
	<% }%>
 </ul>
</body>
</html>