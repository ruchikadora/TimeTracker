<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fetched details</title>
</head>
<body>
<table>
    <tr>
    <h4>Person Time Record Details: ${personEmail}</h4>
    </tr>
    <ul>
    <c:if test="${not empty personDetails}">
    <c:forEach items="${personDetails}" var="personDetail">
        <li>${personDetail}</li><br />
    </c:forEach>
    </c:if>
    </ul>
    </td></tr>
</table>
</body>
</html>