<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%	String color = "FFFFFF";
	Object bgcol=session.getAttribute("pickedBgCol");
	if(bgcol!=null){
		color=(String)bgcol;
	}	
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Home</title>
	</head>

	<body style="background-color:#<%=color%>;">
		<p>
			<a href="colors.jsp">Background color chooser</a>
			<br>
			<a href="trigonometric?a=0&b=90">Sin and cos values in first quadrant</a>
			<br>
			<a href="stories/funny.jsp">A funny story</a>
			<br>
			<a href="powers?a=1&b=100&n=3">Table of powers</a>
			<br>
			<a href="appinfo.jsp">App info</a>
		</p>
		<form action="trigonometric" method="GET">
			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
		</form>
	</body>
</html>