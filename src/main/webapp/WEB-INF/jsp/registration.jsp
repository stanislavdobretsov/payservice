<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/registration.js" />"></script>
    <title>Registration</title>
</head>
<body style="padding: 50px;">
<h3>Registration</h3>
<form:form method="POST" action="/registration" modelAttribute="client">
    <table>
        <tr>
            <td><form:label path="phoneNumber">Phone number</form:label></td>
            <td><form:input id="phoneNumber" path="phoneNumber"/></td>
            <td><form:errors path="phoneNumber"/></td><br/><br/>
            <td><label style="color: red;" hidden="true" id="numberExists">User with the same phone number already exists</label><br/>
        </tr>
        <tr>
            <td><form:label path="email">Email</form:label></td>
            <td><form:input id="email" path="email"/></td>
            <td><form:errors path="email"/></td>
        </tr>
        <tr>
            <td><input id="register" type="submit" value="Register"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>