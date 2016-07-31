<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="RETURN_CAR" bundle="${lang}"/></title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<c:if test="${not empty sessionScope.error}">
    <fmt:message key="${sessionScope.error}" bundle="${lang}"/>
</c:if>
<fmt:message key="RETURN_CAR" bundle="${lang}"/>
<fmt:message key="FOR_FOLLOWING_ORDER" bundle="${lang}"/>
<%--SHOW ORDER INFO--%>
<%@include file="/WEB-INF/jsp/layout/fragments/one_order.jsp" %>

<form name="completeOrder" action="${pageContext.request.contextPath}/pages/admin_complete_order"
      method="post">
    <input type="hidden" name="order_id" value="${sessionScope.order.idOrder}"/>
    <mytag:csrfTag name="token"/>
    <br><fmt:message key="COMMENT" bundle="${lang}"/><br>
    <input type="text" name="comment" value=""/>
    <br><fmt:message key="FINE" bundle="${lang}"/><br>
    <input type="number" name="fine" value="0" min="0"
           placeholder="<fmt:message key="VALUE_MUST_BE_GE_ZERO" bundle="${lang}"/>" required/>
    <input type="submit" name="button" value="<fmt:message key="COMPLETE" bundle="${lang}"/>"/>
</form>
</body>
</html>
