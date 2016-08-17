<table class="table  table-condensed">
    <th><fmt:message key="NAME" bundle="${lang}"/></th>
    <th><fmt:message key="VALUE" bundle="${lang}"/></th>
    <tr>
        <td><fmt:message key="COMMENT" bundle="${lang}"/>:</td>
        <td>${requestScope.order.comment}</td>
    </tr>
    <tr>
        <td><fmt:message key="SUMM" bundle="${lang}"/>:</td>
        <td>${requestScope.order.fine}</td>
    </tr>
    <tr>
        <td><fmt:message key="ORDER_ID" bundle="${lang}"/>:</td>
        <td>${requestScope.order.idOrder}</td>
    </tr>
    <tr>
        <td><fmt:message key="RECEIVER" bundle="${lang}"/>:</td>
        <td><fmt:message key="RECEIVER_NAME" bundle="${lang}"/></td>
    </tr>
    <tr>
        <td><fmt:message key="RECEIVER_CODE" bundle="${lang}"/>:</td>
        <td>14360xxx</td>
    </tr>
    <tr>
        <td><fmt:message key="BANK_CODE" bundle="${lang}"/>:</td>
        <td>305xxx</td>
    </tr>
    <tr>
        <td><fmt:message key="RECEIVER_ACCOUNT" bundle="${lang}"/>:</td>
        <td>2924482550xxxx</td>
    </tr>
</table>