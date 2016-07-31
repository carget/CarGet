<%--
  Created by IntelliJ IDEA.
  User: U
  Date: 21.07.2016
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/WEB-INF/jsp/layout/header.jsp" %>
<html>
<head>
    <title><fmt:message key="REGISTRATION" bundle="${lang}"/></title>
</head>
<body>
<%--MENU--%>
<%@include file="/WEB-INF/jsp/layout/menu/menu.jsp" %>
<form name="registration" action="${pageContext.request.contextPath}/pages/guest_registration"
      method="post">
    <mytag:csrfTag name="token"/>
    <fmt:message key="FIRST_NAME" bundle="${lang}"/><br/> <input type="text" name="firstName" required/><br>
    <fmt:message key="LAST_NAME" bundle="${lang}"/><br/><input type="text" name="lastName" required/><br>
    <fmt:message key="PASSPORT_FORMAT" bundle="${lang}"/><br/><input type="text" name="passport" required/><br>
    <fmt:message key="EMAIL" bundle="${lang}"/><br/><input type="text" name="email"
                                                           pattern="^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$" required/><br>
    <fmt:message key="PASSWORD" bundle="${lang}"/><br/><input type="text" name="password" required/><br>
    <fmt:message key="REPEAT_PASSWORD" bundle="${lang}"/><br/><input type="text" name="password_repeat"/>
    <input type="submit" name="command" value="OK"/>
</form>
<%@include file="/WEB-INF/jsp/layout/footer.jsp" %>
</body>
</html>
