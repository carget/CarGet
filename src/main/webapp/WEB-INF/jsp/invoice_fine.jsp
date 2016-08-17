<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="INVOICE_FOR_FINE" bundle="${lang}"/></h3>
<%--SHOW INVOICE TABLE--%>
<%@include file="/WEB-INF/jsp/layout/fragments/fine_table.jsp" %>

<%--SHOW BUTTON GOTO MY_ORDERS--%>
<br>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/pages/user_my_orders">
    <fmt:message key="MY_ORDERS" bundle="${lang}"/>
</a>
<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>
