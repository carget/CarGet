<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="ORDER_DATA" bundle="${lang}"/></title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<fmt:message key="YOU_CHOOSE" bundle="${lang}"/>
<%--SHOW CAR INFO--%>
<%@include file="/WEB-INF/jsp/layout/fragments/one_car.jsp" %>
<br>
<form name="checkLogin" action="${pageContext.request.contextPath}/pages/user_process_order"
      method="post">
    <fmt:message key="START_DATE" bundle="${lang}"/><br/>
    <mytag:csrfTag name="token"/>
    <%--TODO!!!! min date today--%>
    <%--TODO!!!! get dates from request--%>
    <input type="date" name="start_date" value="${sessionScope.car_filter.selectedStartDate}"/><br/>
    <fmt:message key="END_DATE" bundle="${lang}"/><br/>
    <input type="date" name="end_date" value="${sessionScope.car_filter.selectedEndDate}" /><br/>
    <c:if test="${sessionScope.user_role eq 'GUEST'}">
        <a href="${pageContext.request.contextPath}/pages/index">
            <fmt:message key="LOGIN" bundle="${lang}"/></a>
        <a href="${pageContext.request.contextPath}/pages/register">
            <fmt:message key="REGISTER" bundle="${lang}"/></a>
    </c:if>
    <c:if test="${sessionScope.user_role eq 'USER'}">
        <input type="submit" name="button" value=<fmt:message key="CONFIRM_ORDER" bundle="${lang}"/>>
    </c:if>
</form>
</body>
</html>
