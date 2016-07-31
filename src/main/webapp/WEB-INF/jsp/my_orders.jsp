<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="MY_ORDERS" bundle="${lang}"/></title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<fmt:message key="MY_ORDERS" bundle="${lang}"/>
<%--SHOW TABLE WITH NEW ORDERS--%>
<%@include file="/WEB-INF/jsp/layout/fragments/my_order_table.jsp" %>
<%@include file="/WEB-INF/jsp/layout/footer.jsp" %>
</body>
</html>
