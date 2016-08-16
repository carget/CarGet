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
    <tr>
        <td>${requestScope.order.idOrder}</td>
        <td>
            <a href="${pageContext.request.contextPath}/pages/car_info?car_id=${requestScope.order.car.idCar}">
                ${requestScope.order.car.model.modelName}
            </a>
        </td>
        <td>${requestScope.order.user.firstName}</td>
        <td>${requestScope.order.user.lastName}</td>
        <td>${requestScope.order.startDate}</td>
        <td>${requestScope.order.endDate}</td>
        <td>${requestScope.order.comment}</td>
        <td>${requestScope.order.rent}</td>
        <td>${requestScope.order.fine}</td>
        <td><fmt:message key="${requestScope.order.statusStr}" bundle="${lang}"/></td>
    </tr>
</table>