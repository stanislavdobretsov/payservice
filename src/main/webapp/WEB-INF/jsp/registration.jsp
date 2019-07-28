<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/registration.js" />"></script>
</head>
<body>
<h3>please register</h3>
<form:form method="POST"
           action="/registration" modelAttribute="client">
    <table>
        <tr>
            <td><form:label path="phoneNumber">number</form:label></td>
            <td><form:input id="phoneNumber" path="phoneNumber"/></td><br/><br/>
            <td><form:errors path="phoneNumber"/></td><br/><br/>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>