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
		<title>Report</title>
	</head>

	<body style="background-color:#<%=color%>;">
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we completed</p>
		<img src="reportImage">
	</body>
</html>