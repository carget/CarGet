<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="ERROR_ROLE" bundle="${lang}"/></title>
</head>
<body>
<img src="${pageContext.request.contextPath}/res/exclamation.jpg"/><br>
<fmt:message key="ERROR_ROLE" bundle="${lang}"/>
<br>
<h1>
    <c:if test="${not empty sessionScope.error}">
        <fmt:message key="${sessionScope.error}" bundle="${lang}"/>
        <c:remove var="error" scope="session"/>
    </c:if>
</h1>
<a href="${pageContext.request.contextPath}/pages/index">
    <fmt:message key="GOTO_MAIN" bundle="${lang}"/></a>
</body>
</html>