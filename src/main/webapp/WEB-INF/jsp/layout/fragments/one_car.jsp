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
    <tr>
        <td>${sessionScope.car.model.brand.brandAbbr}</td>
        <td>${sessionScope.car.model.modelName}</td>
        <td>${sessionScope.car.year}</td>
        <td>${sessionScope.car.model.power}</td>
        <td>${sessionScope.car.model.className}</td>
        <td>${sessionScope.car.model.doorsQty}</td>
        <td><fmt:message key="${sessionScope.car.model.automat}" bundle="${lang}"/></td>
        <td><fmt:message key="${sessionScope.car.model.condition}" bundle="${lang}"/></td>
        <td><fmt:message key="${sessionScope.car.fuelType}" bundle="${lang}"/></td>
        <td>${sessionScope.car.pricePerDay}</td>
    </tr>
</table>
