<%--
  Created by IntelliJ IDEA.
  User: U
  Date: 22.07.2016
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="CHOOSE_AUTO" bundle="${lang}"/></title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>

<%--SHOW FILTER--%>
<%@include file="/WEB-INF/jsp/layout/fragments/car_filter.jsp" %>

<%--SHOW TABLE WITH CARS--%>
<%@include file="/WEB-INF/jsp/layout/fragments/car_table.jsp" %>

<%@include file="/WEB-INF/jsp/layout/footer.jsp" %>
</body>
</html>
