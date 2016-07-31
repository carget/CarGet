<%--
  Created by IntelliJ IDEA.
  User: U
  Date: 16.07.2016
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/WEB-INF/jsp/layout/header.jsp" %>

<html>
<head>
    <title><fmt:message key="CARGET_TITLE" bundle="${lang}"/> </title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<%--SHOW login form only if user==guest--%>
<c:if test="${empty sessionScope.user_role or sessionScope.user_role=='GUEST'}">
    <form name="checkLogin" action="${pageContext.request.contextPath}/pages/guest_login"
          method="post">
        <mytag:csrfTag name="token"/>
        <fmt:message key="EMAIL" bundle="${lang}"/><br/>
        <input type="text" name="email" value=""/><br/>
        <fmt:message key="PASSWORD" bundle="${lang}"/><br/>
        <input type="password" name="password" value=""/>
        <input type="submit" name="button" value=<fmt:message key="ENTER" bundle="${lang}"/>>
    </form>
</c:if>

<%@include file="/WEB-INF/jsp/layout/footer.jsp" %>

<mytag:csrfTag name="token"/>
</body>
</html>
