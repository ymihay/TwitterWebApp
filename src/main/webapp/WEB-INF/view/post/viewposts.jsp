<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

<h2>Posts:</h2>
<table border="1">
    <thead>
    <tr>
        <th>No</th>
        <th>Post</th>
        <c:if test="${isLoggedUser == true}">
            <th>Actions</th>
        </c:if>
    </tr>
    </thead>
    <c:set value="0" scope="page" var="counter"/>
    <c:forEach var="currentPost" items="${posts}">
        <c:set value="${counter + 1}" var="counter"/>
        <tr>
            <td>${counter}</td>
            <td>${currentPost.postMessage}</td>
            <td>
                <c:if test="${isLoggedUser == true}">
                    <a href="/pages/modifypost?postid=${currentPost.postId}">Update</a>
                    <a href="/pages/removepost?postid=${currentPost.postId}">Remove</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>