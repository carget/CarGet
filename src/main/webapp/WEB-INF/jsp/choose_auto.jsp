
<%@include file="/WEB-INF/jsp/layout/header.jspf" %>

<h3 align="center"><fmt:message key="CHOOSE_AUTO" bundle="${lang}"/> </h3>

<%--SHOW FILTER--%>
<%@include file="/WEB-INF/jsp/layout/fragments/car_filter.jsp" %>

<%--SHOW TABLE WITH CARS--%>
<%@include file="/WEB-INF/jsp/layout/fragments/car_table.jsp" %>

<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>

