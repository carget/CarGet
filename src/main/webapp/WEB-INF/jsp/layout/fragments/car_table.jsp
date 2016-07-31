<table>
    <th><fmt:message key="BRAND" bundle="${lang}"/></th>
    <th><fmt:message key="MODEL" bundle="${lang}"/></th>
    <th><fmt:message key="YEAR" bundle="${lang}"/></th>
    <th><fmt:message key="POWER" bundle="${lang}"/></th>
    <th><fmt:message key="CLASS" bundle="${lang}"/></th>
    <th><fmt:message key="DOORS_QTY" bundle="${lang}"/></th>
    <th><fmt:message key="AUTOMAT" bundle="${lang}"/></th>
    <th><fmt:message key="CONDITIONING" bundle="${lang}"/></th>
    <th><fmt:message key="FUEL" bundle="${lang}"/></th>
    <th><fmt:message key="PRICE" bundle="${lang}"/></th>
    <th><fmt:message key="ORDER" bundle="${lang}"/></th>
    <c:forEach var="car" items="${sessionScope.cars}">
        <tr>
            <td>${car.model.brand.brandAbbr}</td>
            <td>${car.model.modelName}</td>
            <td>${car.year}</td>
            <td>${car.model.power}</td>
            <td>${car.model.className}</td>
            <td>${car.model.doorsQty}</td>
            <td><fmt:message key="${car.model.automat}" bundle="${lang}"/></td>
            <td><fmt:message key="${car.model.condition}" bundle="${lang}"/></td>
            <td><fmt:message key="${car.fuelType}" bundle="${lang}"/></td>
            <td>${car.pricePerDay}</td>
            <td>
                <form action="${pageContext.request.contextPath}/pages/user_prepare_order"
                      method="get">
                    <input type="hidden" name="car_id" value="${car.idCar}"/>
                    <input type="hidden" name="start_date"
                           value="${sessionScope.car_filter.selectedStartDate}"/>
                    <input type="hidden" name="end_date"
                           value="${sessionScope.car_filter.selectedEndDate}"/>
                    <c:if test="${sessionScope.user_role eq 'GUEST'}">
                        <a href="${pageContext.request.contextPath}/pages/index">
                            <fmt:message key="ENTER" bundle="${lang}"/></a>
                        <a href="${pageContext.request.contextPath}/pages/register">
                            <fmt:message key="REGISTER" bundle="${lang}"/></a>
                    </c:if>
                    <c:if test="${sessionScope.user_role eq 'USER'}">
                        <input type="submit" value="<fmt:message key="ORDER" bundle="${lang}"/>">
                    </c:if>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:remove var="cars" scope="session"/>
</table>