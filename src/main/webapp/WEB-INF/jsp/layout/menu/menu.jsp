<a href="${pageContext.request.contextPath}/pages/change_lang?language=RU"><fmt:message key="RUS"
                                                                                        bundle="${lang}"/></a>
<a href="${pageContext.request.contextPath}/pages/change_lang?language=EN"><fmt:message key="ENG"
                                                                                        bundle="${lang}"/></a>

<%--<form action="${pageContext.request.contextPath}/pages/change_lang" method="get">
    <input type="hidden" name="language" value="RU"/>
    <input type="submit" value="<fmt:message key="RUS" bundle="${lang}"/>">
</form>

<form action="${pageContext.request.contextPath}/pages/change_lang" method="get">
    <input type="hidden" name="language" value="EN"/>
    <input type="submit" value="<fmt:message key="ENG" bundle="${lang}"/>">
</form>--%>
<%--MENU--%>
<%--SHOW CHOOSE AUTO OPTION--%>
<c:if test="${sessionScope.user_role!='ADMIN'}">
    <form action="${pageContext.request.contextPath}/pages/choose_auto" method="get">
        <input type="submit" value="<fmt:message key="CHOOSE_AUTO" bundle="${lang}"/>">
    </form>
</c:if>
<%--REGISTER--%>
<c:choose>
    <c:when test="${sessionScope.user_role=='ADMIN' or sessionScope.user_role=='USER'}">
        <%--LOGOUT--%>
        <form action="${pageContext.request.contextPath}/pages/logout" method="get">
            <input type="submit" value="<fmt:message key="LOGOUT" bundle="${lang}"/>">
        </form>
    </c:when>
    <c:otherwise>
        <form action="${pageContext.request.contextPath}/pages/guest_register" method="get">
            <input type="submit" value="<fmt:message key="REGISTER" bundle="${lang}"/>">
        </form>
        <%--LOGIN--%>
        <form action="${pageContext.request.contextPath}/pages/index" method="get">
            <input type="submit" value="<fmt:message key="ENTER" bundle="${lang}"/>">
        </form>
    </c:otherwise>
</c:choose>
<%--LOGIN--%>
<%--<form action="${pageContext.request.contextPath}/pages/login" method="get">--%>
<%--<input type="hidden" name="command" value="main_page"/>--%>
<%--<input type="submit" value="<fmt:message key="LOGIN" bun    dle="${lang}"/>">--%>
<%--</form>--%>
<%--USER ROLE DEPENDANT--%>
<c:if test="${sessionScope.user_role=='USER'}">
    <%@include file="/WEB-INF/jsp/layout/menu/user_menu.jsp" %>
</c:if>
<c:if test="${sessionScope.user_role=='ADMIN'}">
    <%@include file="/WEB-INF/jsp/layout/menu/admin_menu.jsp" %>
</c:if>
<br>
<fmt:message key="HELLO" bundle="${lang}"/>
<c:out value=" "/><fmt:message key="${sessionScope.user_role}" bundle="${lang}"/>
<c:if test="${not empty sessionScope.user_name}">
    <c:out value="(${sessionScope.user_name})"/>
</c:if>
<hr>
<%--SHOW ERROR MESSAGE IF EXISTS--%>
<c:if test="${not empty sessionScope.error}">
    <fmt:message key="${sessionScope.error}" bundle="${lang}"/>
    <c:remove var="error" scope="session"/>
</c:if>
<hr>
