<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Show</th>
    </tr>
    </thead>

    <c:forEach var="currentUser" items="${users}">
        <tr>
            <td>${currentUser.userId}</td>
            <td>${currentUser.login}</td>
            <td>
                <form method="get" action="viewuser">
                    <input type="hidden" name="userid" value="${currentUser.userId}"/>
                    <input type="submit" value="Show"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>