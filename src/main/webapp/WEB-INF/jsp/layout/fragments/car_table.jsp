<table class="table  table-condensed">
    <th><fmt:message key="PHOTO" bundle="${lang}"/></th>
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
    <c:forEach var="car" items="${requestScope.cars}">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/pages/car_info?car_id=${car.idCar}">
                    <img src="${car.model.img}" height="128" />
                </a>
            </td>
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
                           value="${requestScope.car_filter.selectedStartDate}"/>
                    <input type="hidden" name="end_date"
                           value="${requestScope.car_filter.selectedEndDate}"/>
                    <div class="form-group">

                        <c:if test="${sessionScope.user_role eq 'GUEST'}">
                            <a class="btn btn-primary btn-sm"
                               href="${pageContext.request.contextPath}/pages/index">
                                <fmt:message key="ENTER" bundle="${lang}"/></a>
                            <a class="btn btn-primary btn-sm"
                               href="${pageContext.request.contextPath}/pages/guest_register">
                                <fmt:message key="REGISTER" bundle="${lang}"/></a>
                        </c:if>
                        <c:if test="${sessionScope.user_role eq 'USER'}">
                            <button type="submit" class="btn btn-primary"><fmt:message key="ORDER"
                                                                                       bundle="${lang}"/></button>
                            <%--<input type="submit"--%>
                            <%--value="<fmt:message key="ORDER" bundle="${lang}"/>">--%>
                        </c:if>
                    </div>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:remove var="cars" scope="session"/>
</table>