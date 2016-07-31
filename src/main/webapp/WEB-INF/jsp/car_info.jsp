<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="CAR_INFO" bundle="${lang}"/> </title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<%--SHOW TABLE WITH ONE CAR--%>
<%@include file="/WEB-INF/jsp/layout/fragments/one_car.jsp" %>
</body>
</html>
