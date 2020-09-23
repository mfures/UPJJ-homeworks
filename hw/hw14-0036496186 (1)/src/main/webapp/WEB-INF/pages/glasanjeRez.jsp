<%@page import="hr.fer.zemris.java.p12.model.PollOptions"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%!@SuppressWarnings("unchecked")%>


<%
	Object res = request.getAttribute("results");
	List<PollOptions> results;
	if (res == null) {
		results = new ArrayList<>();
	} else {
		results=(List<PollOptions>) res;
	}
	int max = 0;
	if (results.size() != 0) {
		max = results.get(0).getVotesCount();
	}
%>

<!DOCTYPE html>
<html>
<head>
<title>Glasaj za bend</title>
</head>

<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja:</p>
	<table border="1">
		<tr>
			<th>Option</th>
			<th>Vote count</th>
		</tr>
		<%
			for(PollOptions result : results) {
		%>
		<tr>
			<td><%=result.getOptionTitle()%></td>
			<td><%=result.getVotesCount()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${poll.id}" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${poll.id}">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
	<%for(int i=0;i<results.size();i++){
		if(results.get(i).getVotesCount()<max) break;%>
		<li><a href="<%=results.get(i).getOptionLink() %>" target="_blank"><%=results.get(i).getOptionTitle()%></a></li>
	<% }%>
 </ul>
</body>
</html>