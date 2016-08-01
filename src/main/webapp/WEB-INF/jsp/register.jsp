<%@include file="/WEB-INF/jsp/layout/header.jspf" %>
<h4 align="center"><fmt:message key="REGISTRATION_GREETING" bundle="${lang}"/></h4>
<form name="registration" action="${pageContext.request.contextPath}/pages/guest_registration"
      method="post">
    <mytag:csrfTag name="token"/>
    <div class="form-group">
        <label for="firstName"><fmt:message key="FIRST_NAME" bundle="${lang}"/></label>
        <input type="text" name="firstName" class="form-control" id="firstName" required
               placeholder="<fmt:message key="FIRST_NAME" bundle="${lang}"/>">
    </div>
    <div class="form-group">
        <label for="lastName"><fmt:message key="LAST_NAME" bundle="${lang}"/></label>
        <input type="text" name="lastName" class="form-control" id="lastName" required
               placeholder="<fmt:message key="LAST_NAME" bundle="${lang}"/>">
    </div>
    <div class="form-group">
        <label for="passport"><fmt:message key="PASSPORT" bundle="${lang}"/></label>
        <input type="text" name="passport" class="form-control" id="passport" required
               placeholder="<fmt:message key="PASSPORT_FORMAT" bundle="${lang}"/>">
    </div>
    <div class="form-group">
        <label for="email"><fmt:message key="EMAIL" bundle="${lang}"/></label>
        <input type="text" name="email" class="form-control" id="email" required
               pattern="^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$"
               placeholder="<fmt:message key="EMAIL" bundle="${lang}"/>">
    </div>
    <div class="form-group">
        <label for="password"><fmt:message key="PASSWORD" bundle="${lang}"/></label>
        <input type="text" name="password" class="form-control" id="password" required
               placeholder="<fmt:message key="PASSWORD" bundle="${lang}"/>">
    </div>
    <div class="form-group">
        <label for="password_repeat"><fmt:message key="REPEAT_PASSWORD" bundle="${lang}"/></label>
        <input type="text" name="password_repeat" class="form-control" id="password_repeat" required
               placeholder="<fmt:message key="REPEAT_PASSWORD" bundle="${lang}"/>">
    </div>
    <button type="submit" class="btn btn-primary">OK</button>
</form>
<%@include file="/WEB-INF/jsp/layout/footer.jspf" %>

