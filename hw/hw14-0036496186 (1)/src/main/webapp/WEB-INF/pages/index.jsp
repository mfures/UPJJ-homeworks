<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Opened polls</title>
</head>

<body>
	<h3>Select one of available polls</h3>
		<c:forEach var="poll" items="${polls}">
    	    <a href="${pageContext.request.contextPath}/servleti/glasanje?pollID=${poll.id}">${poll.message}</a> <br>
	    </c:forEach>
 </body>
</html>