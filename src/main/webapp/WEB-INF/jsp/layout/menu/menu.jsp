<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/pages/index">
                <span class="glyphicons glyphicons-home"></span>CarGet</a>
        </div>
        <ul class="nav navbar-nav">
            <%--SHOW CHOOSE AUTO OPTION--%>
            <c:if test="${sessionScope.user_role!='ADMIN'}">
                <li>
                    <a href="${pageContext.request.contextPath}/pages/choose_auto">
                        <fmt:message key="CHOOSE_AUTO" bundle="${lang}"/>
                    </a>
                </li>
            </c:if>
            <%--USER ROLE DEPENDANT--%>
            <c:if test="${sessionScope.user_role=='USER'}">
                <%@include file="/WEB-INF/jsp/layout/menu/user_menu.jsp" %>
            </c:if>
            <c:if test="${sessionScope.user_role=='ADMIN'}">
                <%@include file="/WEB-INF/jsp/layout/menu/admin_menu.jsp" %>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <%--REGISTER--%>
            <c:choose>
                <c:when test="${sessionScope.user_role=='ADMIN' or sessionScope.user_role=='USER'}">
                    <li>
                            <%--LOGOUT--%>
                        <a href="${pageContext.request.contextPath}/pages/logout">
                        <span class="glyphicon glyphicon-log-out"></span>
                                <fmt:message key="LOGOUT" bundle="${lang}"/>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <%--LOGIN--%>
                    <li>
                        <a href="${pageContext.request.contextPath}/pages/index">
                        <span class="glyphicon glyphicon-log-in"></span>
                                <fmt:message key="ENTER" bundle="${lang}"/>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/pages/guest_register">
                            <span class="glyphicon glyphicon-user"></span>
                            <fmt:message key="REGISTER" bundle="${lang}"/>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
            <li>
                <a href="${pageContext.request.contextPath}/pages/change_lang?language=RU">
                    <fmt:message key="RUS" bundle="${lang}"/></a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/pages/change_lang?language=EN">
                    <fmt:message key="ENG" bundle="${lang}"/></a>
            </li>

        </ul>
    </div>
</nav>