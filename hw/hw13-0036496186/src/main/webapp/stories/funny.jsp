<%@page import="java.util.Random"%>
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

	String[] colors = new String[] { "red", "green", "blue", "yellow", "cyan", "white", "black" };
	int ran = (new Random()).nextInt(colors.length);
%>

<!DOCTYPE html>
<html>
<head>
<title>Funny story</title>
</head>

<body style="background-color:#<%=color%>;">
	<p style="width:300px;color:<%=colors[ran]%>;">One day when I was
		3 I decided I wanted to be like my mom and wear “big girl” panties. I
		sneakily went through her drawer and grabbed the first thing I could
		find – a thong (I didn’t know what it was at the time). She didn’t
		know until we went to breakfast with some friends and took me to the
		bathroom. She still won’t let me live it down!</p>
</body>
</html>