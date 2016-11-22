<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
    <jsp:include page="_menu.jsp"/>
    <h2><spring:message code="label.homepage.title"/></h2>
    <c:if test="${empty pageContext.request.userPrincipal.name}">
		<spring:message code="text.homepage.description"/>
    </c:if>
</body>
</html>