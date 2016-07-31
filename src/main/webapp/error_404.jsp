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
    <title><fmt:message key="ERROR" bundle="${lang}"/> 404</title>
</head>
<body>
<img src="${pageContext.request.contextPath}/res/404.png"/><br>
<fmt:message key="ERROR_404" bundle="${lang}"/>
<br>
<a href="${pageContext.request.contextPath}/pages/index">
    <fmt:message key="GOTO_MAIN" bundle="${lang}"/></a>
</body>
</html>