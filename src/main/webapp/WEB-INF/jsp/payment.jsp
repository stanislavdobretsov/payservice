<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Make payment</title>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/payment.js" />"></script>
</head>
<body style="padding: 50px;">
<h3>Make payment</h3>
<form:form method="POST" action="/payment" modelAttribute="payOperation">
    <table>
        <tr>
            <td><form:label path="serviceId">Service</form:label></td><br/>
            <td><form:select id="servicepicker" path="serviceId" style="width: 50px;">
                <c:forEach items="${services}" var="service">
                    <option value="${service.serviceId}">${service.title}</option>
                </c:forEach>
            </form:select></td>
        </tr>
        <tr>
            <td>
                <c:forEach items="${services}" var="service">
                    <label class="payinf" id="${service.title}" hidden="hidden">${'Min pay sum is ' += service.minPaySum += ' , max pay sum is ' += service.maxPaySum}</label>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td><form:label path="paySum">Pay sum</form:label></td>
            <td><form:input id="paySum" path="paySum"/></td>
            <td><form:errors path="paySum"/></td>
        </tr>
        <tr>
            <td><form:label path="payAccountNumber">Account number</form:label></td>
            <td><form:input id="payAccountNumber" path="payAccountNumber"/></td>
            <td><form:errors path="payAccountNumber"/></td>
        </tr>
        <tr>
            <td><input id="pay" type="submit" value="Pay"/></td>
        </tr>
    </table>
</form:form>
<a href="account">Back to account</a>
</body>
</html>