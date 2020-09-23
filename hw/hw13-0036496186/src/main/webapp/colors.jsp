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
		<title>Select background color</title>
	</head>

	<body style="background-color:#<%=color%>;">
		<a href="${pageContext.request.contextPath}/setcolor?bgColor=FFFFFF">WHITE</a>
		<a href="${pageContext.request.contextPath}/setcolor?bgColor=FF0000">RED</a>
		<a href="${pageContext.request.contextPath}/setcolor?bgColor=00FF00">GREEN</a>
		<a href="${pageContext.request.contextPath}/setcolor?bgColor=00FFFF">CYAN</a>
	</body>
</html>