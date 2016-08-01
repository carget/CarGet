<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="COMPLETED_ORDERS" bundle="${lang}"/> </h3>
<%--SHOW TABLE WITH ORDERS--%>
<%@include file="/WEB-INF/jsp/layout/fragments/completed_orders_table.jsp" %>

<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>
