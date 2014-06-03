<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Twitter Web Application</title>
</head>
<body>
Please, login
<br/><br/>
<form action="login" method="post">
    <div>Login: <input name="user" type="text"></div>
    <div>Password: <input name="password" type="password"></div>
    <button type="submit" value="login">Login</button>
    <button type="reset">Clear</button>
</form>

<br/><br/>

Or <a href="<c:url value="/pages/register"/>">register</a>

</body>
</html>
