<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Report payments</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/payments-report-style.css"/>"/>
</head>
<body style="padding: 50px;">
<h3>Report payments</h3>
<div id="filter">
    <form:form method="POST" action="/reportpayments" modelAttribute="filter">
        <table>
            <tr>
                <td><form:label path="serviceId">Service</form:label></td><br/>
                <td><form:select path="serviceId" style="width: 50px;">
                    <c:forEach items="${services}" var="service">
                        <option value="${service['serviceId']}">${service['title']}</option>
                    </c:forEach>
                </form:select></td>
            </tr>
            <tr>
                <td><form:label path="paySum">Pay sum</form:label></td>
                <td><form:input type="text" id="paySum" path="paySum"/></td>
            </tr>
            <tr>
                <td><form:label path="begin">Start date</form:label></td>
                <td><form:input type="datetime-local" id="begin" path="begin"/></td>
            </tr>
            <tr>
                <td><form:label path="end">End date</form:label></td>
                <td><form:input type="datetime-local" id="end" path="end"/></td>
            </tr>
            <tr>
                <td><input id="report" type="submit" value="Report"/></td>
            </tr>
        </table>
        <a href="account">Back to account</a>
    </form:form>
</div>
<div id="result">
    <table>
        <div>
            <tr>
                <td><label>Account number</label></td>
                <td><label>Pay Sym</label></td>
                <td><label>Operation Time</label></td>
            </tr>
            <c:forEach items="${results}" var="result">
                <tr>
                    <td><label>${result.payAccountNumber}</label></td>
                    <td><label>${result.paySum}</label></td>
                    <td><label><javatime:format value="${result.operationTime}" pattern="dd.MM.yyyy HH:mm" /></label></td>
                </tr>
            </c:forEach>
        </div>
    </table>
</div>
</body>
</html>