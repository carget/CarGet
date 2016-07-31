<br>
<form name="CarFilter" action="${pageContext.request.contextPath}/pages/choose_auto"
      method="get">
    <fmt:message key="BRAND" bundle="${lang}"/>
    <%--FILL BRANDS FROM FILTER OBJECT--%>
    <select size="1" name="brand_id">
        <option value="-1">
            <fmt:message key="ALL" bundle="${lang}"/>
        </option>
        <c:forEach var="brand" items="${sessionScope.car_filter.brands}">
            <option value="${brand.idBrand}"
                    <c:out value="${brand.idBrand == sessionScope.car_filter.selectedBrandId ?
                    'selected' : '' }"/>>
                    ${brand.brandAbbr}
            </option>
        </c:forEach>
    </select>
    <fmt:message key="YEAR" bundle="${lang}"/>
    <%--FILL YEAR FROM FILTER OBJECT--%>
    <select size="1" name="year">
        <option value="-1">
            <fmt:message key="ALL" bundle="${lang}"/>
        </option>
        <c:forEach var="year" items="${sessionScope.car_filter.years}">
            <option value="${year}"
                    <c:out value="${year == sessionScope.car_filter.selectedYear ?
                    'selected' : '' }"/>>
                    ${year}
            </option>
        </c:forEach>
    </select>
    <fmt:message key="AUTOMAT" bundle="${lang}"/>
    <%--FILL AUTOMAT FROM FILTER OBJECT--%>
    <select size="1" name="automat">
        <option value="-1"
                <c:out value="${sessionScope.car_filter.selectedAutomat==-1 ?
                    'selected' : '' }"/>>
            <fmt:message key="ALL" bundle="${lang}"/>
        </option>
        <option value="1"
                <c:out value="${sessionScope.car_filter.selectedAutomat==1 ?
                    'selected' : '' }"/>>
            <fmt:message key="true" bundle="${lang}"/>
        </option>
        <option value="0"
                <c:out value="${sessionScope.car_filter.selectedAutomat==0 ?
                    'selected' : '' }"/>>
            <fmt:message key="false" bundle="${lang}"/>
        </option>
    </select>
    <fmt:message key="CONDITIONING" bundle="${lang}"/>
    <%--FILL CONDITIONING FROM FILTER OBJECT--%>
    <select size="1" name="condition">
        <option value="-1"
                <c:out value="${sessionScope.car_filter.selectedCondition==-1 ?
                    'selected' : '' }"/>>
            <fmt:message key="ALL" bundle="${lang}"/>
        </option>
        <option value="1"
                <c:out value="${sessionScope.car_filter.selectedCondition==1 ?
                    'selected' : '' }"/>>
            <fmt:message key="true" bundle="${lang}"/>
        </option>
        <option value="0"
                <c:out value="${sessionScope.car_filter.selectedCondition==0 ?
                    'selected' : '' }"/>>
            <fmt:message key="false" bundle="${lang}"/>
        </option>
    </select>
    <fmt:message key="FUEL" bundle="${lang}"/>
    <%--FILL FUEL TYPE FROM FILTER OBJECT--%>
    <select size="1" name="fuel_type">
        <option value="ALL">
            <fmt:message key="ALL" bundle="${lang}"/>
        </option>
        <c:forEach var="fuel" items="${sessionScope.car_filter.fuelTypes}">
            <option value="${fuel}"
                    <c:out value="${fuel == sessionScope.car_filter.selectedFuelType ?
                    'selected' : '' }"/>>
                <fmt:message key="${fuel}" bundle="${lang}"/>
            </option>
        </c:forEach>
    </select>
    <br><fmt:message key="START_DATE" bundle="${lang}"/>
    <input type="date" name="start_date" value="${sessionScope.car_filter.selectedStartDate}"
           required/>
    <br><fmt:message key="END_DATE" bundle="${lang}"/>
    <input type="date" name="end_date" value="${sessionScope.car_filter.selectedEndDate}" required/>
    <input type="submit" value="<fmt:message key="FIND" bundle="${lang}"/>">
</form>

