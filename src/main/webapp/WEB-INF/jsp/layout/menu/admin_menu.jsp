<%--SHOW ADMIN MENU--%>
<li>
    <a href="${pageContext.request.contextPath}/pages/admin_new_orders">
        <fmt:message key="NEW_ORDERS" bundle="${lang}"/>
    </a>
</li>
<li>
    <a href="${pageContext.request.contextPath}/pages/admin_approved_orders">
        <fmt:message key="APPROVED_ORDERS" bundle="${lang}"/>
    </a>
<li>
<li>
    <a href="${pageContext.request.contextPath}/pages/admin_paid_orders">
        <fmt:message key="PAID_ORDERS" bundle="${lang}"/>
    </a>
<li>
    <a href="${pageContext.request.contextPath}/pages/admin_rejected_orders">
        <fmt:message key="REJECTED_ORDERS" bundle="${lang}"/>
    </a>
</li>
<li>
    <a href="${pageContext.request.contextPath}/pages/admin_completed_orders">
        <fmt:message key="COMPLETED_ORDERS" bundle="${lang}"/>
    </a>
</li>
<li>
    <a href="${pageContext.request.contextPath}/pages/admin_canceled_orders">
        <fmt:message key="CANCELED_ORDERS" bundle="${lang}"/>
    </a>
</li>
