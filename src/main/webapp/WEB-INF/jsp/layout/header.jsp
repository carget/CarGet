<%--
  Created by IntelliJ IDEA.
  User: U
  Date: 17.07.2016
  Time: 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tld/csrftag_lib.tld" prefix="mytag" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>


