<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>


<!DOCTYPE html>
<html>
<head>
<title>Vote</title>
</head>

<body>
	<h1>${poll.title}</h1>
	<p>${poll.message}</p>
	<ol>
	    <c:forEach var="option" items="${pollOptions}">
        <li><a href="glasanje-glasaj?id=${option.id}">${option.optionTitle}</a></li>
    	</c:forEach>
	</ol>
 </body>
</html>