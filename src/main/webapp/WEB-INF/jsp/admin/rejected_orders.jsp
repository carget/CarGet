<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="REJECTED_ORDERS" bundle="${lang}"/></title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<c:if test="${not empty sessionScope.error}">
    <fmt:message key="${sessionScope.error}" bundle="${lang}"/>
</c:if>
<fmt:message key="REJECTED_ORDERS" bundle="${lang}"/>
<%--SHOW TABLE WITH ORDERS--%>
<%@include file="/WEB-INF/jsp/layout/fragments/rejected_orders_table.jsp" %>
</body>
</html>
