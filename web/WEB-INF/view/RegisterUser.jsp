<%--
    Document : AddStudent
    Created on : Apr 25, 2014, 2:58:06 PM
    Author : andrii
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>
</head>
<body>
<h1>Register an user now</h1>
<form method="post" action="adduser">
    <table>
        <tr>
            <td>First name</td>
            <td><input type="text" name="sFirstName"/></td>
        </tr>
        <tr>
            <td>Patronymic</td>
            <td><input type="text" name="sPatronymic"/></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><input type="text" name="sLastName"/> <br/></td>
        </tr>
        <tr>
            <td>Login</td>
            <td><input type="text" name="sLogin" required="true" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="sPassword" required="true" /></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td><input type="date" name="sBirthDate"/></td>
        </tr>
        <tr>
            <td>Sex</td>
            <td><input type="text" name="sSex"/></td>
        </tr>
        <tr>
            <td>Country</td>
            <td><input type="text" name="sCountry" /></td>
        </tr>
    </table>
    <button type="submit">Register</button>
    <button type="reset">Reset</button>
    <button type="button">Cancel</button>
</form>
</body>
</html>