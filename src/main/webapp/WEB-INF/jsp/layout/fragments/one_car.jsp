<table class="table">
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
        <td>${requestScope.car.model.brand.brandAbbr}</td>
        <td>${requestScope.car.model.modelName}</td>
        <td>${requestScope.car.year}</td>
        <td>${requestScope.car.model.power}</td>
        <td>${requestScope.car.model.className}</td>
        <td>${requestScope.car.model.doorsQty}</td>
        <td><fmt:message key="${requestScope.car.model.automat}" bundle="${lang}"/></td>
        <td><fmt:message key="${requestScope.car.model.condition}" bundle="${lang}"/></td>
        <td><fmt:message key="${requestScope.car.fuelType}" bundle="${lang}"/></td>
        <td>${requestScope.car.pricePerDay}</td>
    </tr>
</table>
