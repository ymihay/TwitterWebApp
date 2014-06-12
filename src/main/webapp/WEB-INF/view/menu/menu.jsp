<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="loggedUser" value="${sessionScope.loggedUserId}" scope="page"></c:set>

<c:if test="${loggedUser != null}">
    <ul>
        <li><a href="<c:url value='/pages/logout'/>">Log out</a></li>
        <li><a href="/pages/modifyuser">Update user profile</a></li>
        <li><a href="/pages/removeuser">Remove a profile</a></li>
        <li><a href="/pages/viewuserposts?userid=${loggedUser}">List of my tweets</a></li>
        <li><a href="/pages/viewfollowingusers?userid=${loggedUser}">List of following users</a></li>
        <li><a href="/pages/viewfollowers?userid=${loggedUser}">List of followers</a></li>
        <li><a href="/pages/viewfollowingposts?userid=${loggedUser}">List of following posts</a></li>
        <li><a href="/pages/createpost">Create a new post</a></li>
        <li><a href="/pages/viewall">View all</a></li>

        <jsp:include page="/WEB-INF/view/search/searchpost.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/view/search/searchuser.jsp"></jsp:include>
    </ul>
</c:if>
</body>
</html>
