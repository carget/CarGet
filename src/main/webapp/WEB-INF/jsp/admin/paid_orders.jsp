<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="PAID_ORDERS" bundle="${lang}"/> </h3>
<%--SHOW TABLE WITH ORDERS--%>
<%@include file="/WEB-INF/jsp/layout/fragments/paid_order_table.jsp" %>

<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>
