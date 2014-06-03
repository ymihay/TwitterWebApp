<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
Click to <a href="<c:url value='/pages/logout'/>">log out</a><br/><br/>
Hi, ${user.login}<br/><br/>
All users: ${users}<br/><br/>

<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
        </tr>
    </thead>

    <c:forEach var="currentUser" items="${users}">
        <tr>
            <td>${currentUser.userId}</td>
            <td>${currentUser.login}</td>
            <td>
                <form method="get" action="viewuser" >
                    <input type="hidden" name="userid" value="${currentUser.userId}" />
                    <input type="submit" value="Show" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
