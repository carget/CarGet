<br>
<form name="CarFilter" action="${pageContext.request.contextPath}/pages/choose_auto"
      method="get">
    <div class="row">
        <div class="col-xs-2">
            <fmt:message key="BRAND" bundle="${lang}"/>
            <%--FILL BRANDS FROM FILTER OBJECT--%>
            <select size="1" class="form-control" name="brand_id">
                <option value="-1">
                    <fmt:message key="ALL" bundle="${lang}"/>
                </option>
                <c:forEach var="brand" items="${requestScope.car_filter.brands}">
                    <option value="${brand.idBrand}"
                            <c:out value="${brand.idBrand == requestScope.car_filter.selectedBrandId ?
                    'selected' : '' }"/>>
                            ${brand.brandAbbr}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-xs-2">
            <fmt:message key="YEAR" bundle="${lang}"/>
            <%--FILL YEAR FROM FILTER OBJECT--%>
            <select size="1" class="form-control" name="year">
                <option value="-1">
                    <fmt:message key="ALL" bundle="${lang}"/>
                </option>
                <c:forEach var="year" items="${requestScope.car_filter.years}">
                    <option value="${year}"
                            <c:out value="${year == requestScope.car_filter.selectedYear ?
                    'selected' : '' }"/>>
                            ${year}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-xs-2">
            <fmt:message key="AUTOMAT" bundle="${lang}"/>
            <%--FILL AUTOMAT FROM FILTER OBJECT--%>
            <select size="1" class="form-control" name="automat">
                <option value="-1"
                        <c:out value="${requestScope.car_filter.selectedAutomat==-1 ?
                    'selected' : '' }"/>>
                    <fmt:message key="ALL" bundle="${lang}"/>
                </option>
                <option value="1"
                        <c:out value="${requestScope.car_filter.selectedAutomat==1 ?
                    'selected' : '' }"/>>
                    <fmt:message key="true" bundle="${lang}"/>
                </option>
                <option value="0"
                        <c:out value="${requestScope.car_filter.selectedAutomat==0 ?
                    'selected' : '' }"/>>
                    <fmt:message key="false" bundle="${lang}"/>
                </option>
            </select>
        </div>
        <div class="col-xs-2">
            <fmt:message key="CONDITIONING" bundle="${lang}"/>
            <%--FILL CONDITIONING FROM FILTER OBJECT--%>
            <select size="1" class="form-control" name="condition">
                <option value="-1"
                        <c:out value="${requestScope.car_filter.selectedCondition==-1 ?
                    'selected' : '' }"/>>
                    <fmt:message key="ALL" bundle="${lang}"/>
                </option>
                <option value="1"
                        <c:out value="${requestScope.car_filter.selectedCondition==1 ?
                    'selected' : '' }"/>>
                    <fmt:message key="true" bundle="${lang}"/>
                </option>
                <option value="0"
                        <c:out value="${requestScope.car_filter.selectedCondition==0 ?
                    'selected' : '' }"/>>
                    <fmt:message key="false" bundle="${lang}"/>
                </option>
            </select>
        </div>
        <div class="col-xs-2">
            <fmt:message key="FUEL" bundle="${lang}"/>
            <%--FILL FUEL TYPE FROM FILTER OBJECT--%>
            <select size="1" class="form-control" name="fuel_type">
                <option value="3" <c:out value="${requestScope.car_filter.selectedFuelType == 3 ?
                 'selected' : '' }"/>>
                    <fmt:message key="ALL" bundle="${lang}"/>
                </option>
                <option value="0" <c:out value="${requestScope.car_filter.selectedFuelType == 0 ?
                 'selected' : '' }"/>>
                    <fmt:message key="PETROL" bundle="${lang}"/>
                </option>
                <option value="1" <c:out value="${requestScope.car_filter.selectedFuelType == 1 ?
                 'selected' : '' }"/>>
                    <fmt:message key="GASOLINE" bundle="${lang}"/>
                </option>
                <option value="2" <c:out value="${requestScope.car_filter.selectedFuelType == 2 ?
                 'selected' : '' }"/>>
                    <fmt:message key="DISEL" bundle="${lang}"/>
                </option>
                <%--<c:forEach var="fuel" items="${requestScope.car_filter.fuelTypes}">--%>
                <%--<option value="${fuel}"--%>
                <%--<c:out value="${fuel == requestScope.car_filter.selectedFuelTypeString ?--%>
                <%--'selected' : '' }"/>>--%>
                <%--<fmt:message key="${fuel}" bundle="${lang}"/>--%>
                <%--</option>--%>
                <%--</c:forEach>--%>
            </select>
        </div>

    </div>
    <div class="row">
        <div class="col-sm-2">
            <div class="form-group">
                <label for="start_date"><fmt:message key="START_DATE" bundle="${lang}"/></label>
                <input type="date" name="start_date" id="start_date"
                       value="${requestScope.car_filter.selectedStartDate}" required/>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="form-group">
                <label for="end_date"><fmt:message key="END_DATE" bundle="${lang}"/></label>
                <input type="date" name="end_date" id="end_date"
                       value="${requestScope.car_filter.selectedEndDate}" required/>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="form-group">
                <label for="low_price"><fmt:message key="MIN_PRICE" bundle="${lang}"/></label>
                <input type="number" name="low_price" id="low_price" min="0"
                       value="${requestScope.car_filter.selectedLowPrice}" required/>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="form-group">
                <label for="hi_price"><fmt:message key="MAX_PRICE" bundle="${lang}"/></label>
                <input type="number" name="hi_price" id="hi_price" min="0"
                       value="${requestScope.car_filter.selectedHiPrice}" required/>
            </div>
        </div>
        <div class="col-sm-2">
            <button type="submit" class="btn btn-primary">
                <fmt:message key="FIND" bundle="${lang}"/></button>
        </div>
    </div>
</form>

