<%--SHOW ADMIN MENU--%>
<form action="${pageContext.request.contextPath}/pages/admin_new_orders" method="get">
    <input type="submit" value="<fmt:message key="NEW_ORDERS" bundle="${lang}"/>">
</form>
<form action="${pageContext.request.contextPath}/pages/admin_approved_orders" method="get">
    <input type="submit" value="<fmt:message key="APPROVED_ORDERS" bundle="${lang}"/>">
</form>
<form action="${pageContext.request.contextPath}/pages/admin_rejected_orders" method="get">
    <input type="submit" value="<fmt:message key="REJECTED_ORDERS" bundle="${lang}"/>">
</form>
<form action="${pageContext.request.contextPath}/pages/admin_completed_orders" method="get">
    <input type="submit" value="<fmt:message key="COMPLETED_ORDERS" bundle="${lang}"/>">
</form>
