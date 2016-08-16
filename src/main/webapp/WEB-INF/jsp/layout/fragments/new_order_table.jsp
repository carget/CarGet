<table class="table">
    <th><fmt:message key="ORDER_ID" bundle="${lang}"/></th>
    <th><fmt:message key="CAR" bundle="${lang}"/></th>
    <th><fmt:message key="FIRST_NAME" bundle="${lang}"/></th>
    <th><fmt:message key="LAST_NAME" bundle="${lang}"/></th>
    <th><fmt:message key="START_DATE" bundle="${lang}"/></th>
    <th><fmt:message key="END_DATE" bundle="${lang}"/></th>
    <%--<th><fmt:message key="COMMENT" bundle="${lang}"/></th>--%>
    <th><fmt:message key="RENT" bundle="${lang}"/></th>
    <th><fmt:message key="FINE" bundle="${lang}"/></th>
    <th><fmt:message key="STATUS" bundle="${lang}"/></th>
    <th><fmt:message key="APPROVE" bundle="${lang}"/></th>
    <th><fmt:message key="REASON" bundle="${lang}"/></th>
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
                <%--<td>${order.comment}</td>--%>
            <td>${order.rent}</td>
            <td>${order.fine}</td>
            <td><fmt:message key="${order.statusStr}" bundle="${lang}"/></td>
            <td>
                <form action="${pageContext.request.contextPath}/pages/admin_approve_order"
                      method="post">
                    <input type="hidden" name="order_id" value="${order.idOrder}"/>
                    <mytag:csrfTag name="token"/>
                    <button type="submit" class="btn btn-primary btn-sm">
                        <fmt:message key="APPROVE" bundle="${lang}"/>
                    </button>
                        <%--<input type="submit" name="command" value="<fmt:message key="APPROVE" bundle="${lang}"/>"/>--%>
                </form>
            </td>
            <td colspan="2">
                <form action="${pageContext.request.contextPath}/pages/admin_reject_order" method="post">
                    <input type="hidden" name="order_id" value="${order.idOrder}"/>
                    <mytag:csrfTag name="token"/>
                    <div class="row">
                        <div class="form-group col-xs-5">
                            <input type="text" name="reason" class="form-control" value="" required/>
                        </div>
                        <div class="form-group col-xs-1">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <fmt:message key="REJECT" bundle="${lang}"/>
                            </button>
                        </div>
                    </div>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:remove var="orders" scope="session"/>
</table>