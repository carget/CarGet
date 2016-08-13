<table class="table">
    <th><fmt:message key="ORDER_ID" bundle="${lang}"/></th>
    <th><fmt:message key="CAR" bundle="${lang}"/></th>
    <th><fmt:message key="FIRST_NAME" bundle="${lang}"/></th>
    <th><fmt:message key="LAST_NAME" bundle="${lang}"/></th>
    <th><fmt:message key="START_DATE" bundle="${lang}"/></th>
    <th><fmt:message key="END_DATE" bundle="${lang}"/></th>
    <th><fmt:message key="COMMENT" bundle="${lang}"/></th>
    <th><fmt:message key="RENT" bundle="${lang}"/></th>
    <th><fmt:message key="FINE" bundle="${lang}"/></th>
    <th><fmt:message key="STATUS" bundle="${lang}"/></th>
    <th><fmt:message key="ACTION" bundle="${lang}"/></th>
    <c:forEach var="order" items="${requestScope.orders}">
        <tr>
            <td>${order.idOrder}</td>
            <td>
                <a href="${pageContext.request.contextPath}/pages/car_info?car_id=${order.car.idCar}">
                    <img src="${order.car.model.img}" height="128" />
                </a>
            </td>
            <td>${order.user.firstName}</td>
            <td>${order.user.lastName}</td>
            <td>${order.startDate}</td>
            <td>${order.endDate}</td>
            <td>${order.comment}</td>
            <td>${order.rent}</td>
            <td>${order.fine}</td>
            <td><fmt:message key="${order.status}" bundle="${lang}"/></td>
            <td>
                <c:if test="${order.status eq 'APPROVED'}">
                    <%--SHOW PAY BUTTON--%>
                    <form action="${pageContext.request.contextPath}/pages/user_payment_order"
                          method="post">
                        <mytag:csrfTag name="token"/>
                        <input type="hidden" name="order_id" value="${order.idOrder}"/>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="PAY" bundle="${lang}"/></button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <c:remove var="orders" scope="session"/>
</table>