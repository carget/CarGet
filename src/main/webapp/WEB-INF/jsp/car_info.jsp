<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="CAR_INFO" bundle="${lang}"/></h3>
<%--SHOW TABLE WITH ONE CAR--%>
<%@include file="/WEB-INF/jsp/layout/fragments/one_car.jsp" %>

<%--SHOW CAR IMAGE--%>
<img class="img-responsive" src="${requestScope.car.model.img}"/>

<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>
