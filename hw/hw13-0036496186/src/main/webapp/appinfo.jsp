<%@page import="java.util.concurrent.TimeUnit"%>
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

	long initTime = (long) request.getServletContext().getAttribute("initTime");
	long duration = System.currentTimeMillis() - initTime;

	long days = TimeUnit.MILLISECONDS.toDays(duration);
	duration -= TimeUnit.DAYS.toMillis(days);

	long hours = TimeUnit.MILLISECONDS.toHours(duration);
	duration -= TimeUnit.HOURS.toMillis(hours);

	long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
	duration -= TimeUnit.MINUTES.toMillis(minutes);

	long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
	duration -= TimeUnit.SECONDS.toMillis(seconds);

	long milis = duration;
%>

<!DOCTYPE html>
<html>
<head>
<title>AppInfo</title>
</head>

<body style="background-color:#<%=color%>;">
	<p>
		Elapsed time:
		<%=new String(days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds and "
					+ milis + " milliseconds")%></p>
</body>
</html>