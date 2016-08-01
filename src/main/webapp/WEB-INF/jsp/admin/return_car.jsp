<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="RETURN_CAR" bundle="${lang}"/></h3>
<h4><fmt:message key="FOR_FOLLOWING_ORDER" bundle="${lang}"/></h4>
<%--SHOW ORDER INFO--%>
<%@include file="/WEB-INF/jsp/layout/fragments/one_order.jsp" %>

<form name="completeOrder" action="${pageContext.request.contextPath}/pages/admin_complete_order"
      method="post">
    <input type="hidden" name="order_id" value="${requestScope.order.idOrder}"/>
    <mytag:csrfTag name="token"/>
    <div class="form-group">
        <label for="comment"><fmt:message key="COMMENT" bundle="${lang}"/></label>
        <input type="text" name="comment" class="form-control" id="comment" required
               placeholder="<fmt:message key="COMMENT" bundle="${lang}"/>">
    </div>
    <div class="form-group">
        <label for="fine"><fmt:message key="FINE" bundle="${lang}"/></label>
        <input type="number" name="fine" class="form-control" id="fine" required
               value="0" min="0" placeholder="<fmt:message key="VALUE_MUST_BE_GE_ZERO" bundle="${lang}"/>">
    </div>
    <button type="submit" class="btn btn-primary">
        <fmt:message key="COMPLETE" bundle="${lang}"/>
    </button>
</form>
<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>
