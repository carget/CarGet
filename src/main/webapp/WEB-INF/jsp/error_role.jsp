<%@include file="/WEB-INF/jsp/layout/header.jspf" %>
<h3 align="center">
<img align="center" src="${pageContext.request.contextPath}/res/exclamation.png"/><br>
<fmt:message key="ERROR_ROLE" bundle="${lang}"/>
<br>
    <c:if test="${not empty sessionScope.error}">
        <fmt:message key="${sessionScope.error}" bundle="${lang}"/>
        <c:remove var="error" scope="session"/>
    </c:if>
<a href="${pageContext.request.contextPath}/pages/index">
    <fmt:message key="GOTO_MAIN" bundle="${lang}"/></a>
</h3>
<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>