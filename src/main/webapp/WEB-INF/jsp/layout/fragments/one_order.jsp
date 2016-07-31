<table>
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
        <td>${sessionScope.order.idOrder}</td>
        <td>
            <a href="${pageContext.request.contextPath}/pages/car_info?car_id=${sessionScope.order.car.idCar}">
                ${sessionScope.order.car.model.modelName}
            </a>
        </td>
        <td>${sessionScope.order.user.firstName}</td>
        <td>${sessionScope.order.user.lastName}</td>
        <td>${sessionScope.order.startDate}</td>
        <td>${sessionScope.order.endDate}</td>
        <td>${sessionScope.order.comment}</td>
        <td>${sessionScope.order.rent}</td>
        <td>${sessionScope.order.fine}</td>
        <td><fmt:message key="${sessionScope.order.status}" bundle="${lang}"/></td>
    </tr>
</table>