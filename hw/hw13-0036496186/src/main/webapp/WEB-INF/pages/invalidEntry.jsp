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
%>

<!DOCTYPE html>
<html>
<head>
<title>Invalid</title>
</head>

<body style="background-color:#<%=color%>;">
	<p>Provided parameters are invalid!</p>
	<p>a (integer from [-100,100]) b (integer from [-100,100]) and n 
	(where n>=1 and n&lt;=5).</p>
</body>
</html>