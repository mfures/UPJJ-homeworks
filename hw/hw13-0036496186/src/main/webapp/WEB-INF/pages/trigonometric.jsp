<%@page import="java.text.DecimalFormat"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>

<%
	String color = "FFFFFF";
	Object bgcol = session.getAttribute("pickedBgCol");
	if (bgcol != null) {
		color = (String) bgcol;
	}

	DecimalFormat formatter = new DecimalFormat("#0.000000");
	double[] sinValues = (double[]) request.getAttribute("sinValues");
	double[] cosValues = (double[]) request.getAttribute("cosValues");
	int a = (int) request.getAttribute("a");
%>

<!DOCTYPE html>
<html>
<head>
<title>Trigonometric</title>
</head>

<body style="background-color:#<%=color%>;">
	<table border="1">
		<tr>
			<th>x</th>
			<th>sin(x)</th>
			<th>cos(x)</th>
		</tr>
		<%
			for (int i = 0; i < sinValues.length; i++) {
		%>
		<tr>
			<td><%=a+i%></td>
			<td><%=formatter.format(sinValues[i])%></td>
			<td><%=formatter.format(cosValues[i])%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>