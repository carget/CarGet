<%@include file="/WEB-INF/jsp/layout/header.jspf" %>
<h3 align="center">
    <img align="center" src="${pageContext.request.contextPath}/res/404.png"/><br>
    <fmt:message key="ERROR_404" bundle="${lang}"/>
    <br>
    <a href="${pageContext.request.contextPath}/pages/index">
        <fmt:message key="GOTO_MAIN" bundle="${lang}"/></a></h3>
<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>