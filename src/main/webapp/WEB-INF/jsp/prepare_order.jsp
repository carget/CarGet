<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="YOU_CHOOSE" bundle="${lang}"/> </h3>
<%@include file="/WEB-INF/jsp/layout/fragments/one_car.jsp" %>
<%--SHOW CAR INFO--%>
<form name="checkLogin" action="${pageContext.request.contextPath}/pages/user_process_order"
      method="post">
    <mytag:csrfTag name="token"/>
    <div class="col-xs-3">
        <div class="form-group">
            <label for="start_date"><fmt:message key="START_DATE" bundle="${lang}"/></label>
            <input type="date" name="start_date" id="start_date"
                   value="${requestScope.start_date}" required/>
        </div>
    </div>
    <div class="col-xs-3">
        <div class="form-group">
            <label for="end_date"><fmt:message key="END_DATE" bundle="${lang}"/></label>
            <input type="date" name="end_date" id="end_date"
                   value="${requestScope.end_date}" required/>
        </div>
    </div>
    <input type="hidden" name="car_id" value="${requestScope.car.idCar}"/><br/>
    <c:if test="${sessionScope.user_role eq 'GUEST'}">
        <a href="${pageContext.request.contextPath}/pages/index">
            <fmt:message key="LOGIN" bundle="${lang}"/></a>
        <a href="${pageContext.request.contextPath}/pages/register">
            <fmt:message key="REGISTER" bundle="${lang}"/></a>
    </c:if>
    <div class="form-group">
        <c:if test="${sessionScope.user_role eq 'USER'}">
            <button type="submit" class="btn btn-primary">
                <fmt:message key="CONFIRM_ORDER" bundle="${lang}"/></button>
        </c:if>
    </div>
</form>
<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>
