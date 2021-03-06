<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/view/error/error.jsp" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

<h2>Profile info</h2>

<table>
    <tr>
        <td>First name</td>
        <td>${user.firstName}</td>
    </tr>
    <tr>
        <td>Patronymic</td>
        <td>${user.patronymic}</td>
    </tr>
    <tr>
        <td>Last name</td>
        <td>${user.lastName}</td>
    </tr>
    <tr>
        <td>Login</td>
        <td>${user.login}</td>
    </tr>
    <tr>
        <td>Birth date</td>
        <td>${user.birthdate}</td>
    </tr>
    <tr>
        <td>Sex</td>
        <td>${user.sex.sexName}</td>
    </tr>
    <tr>
        <td>Country</td>
        <td>${user.country.countryName}</td>
    </tr>
</table>


<ul>
    <li><a href="/pages/viewuserposts?userid=${user.userId}">List of tweets</a></li>
    <li><a href="/pages/viewfollowingusers?userid=${user.userId}">List of following users</a></li>
    <li><a href="/pages/viewfollowers?userid=${user.userId}">List of followers</a></li>
    <li><a href="/pages/viewfollowingposts?userid=${user.userId}">List of following posts</a></li>
    <c:if test="${(isFollowing != null) && (!isFollowing)}">
        <li><a href="/pages/setsubscription?userid=${user.userId}">Follow</a></li>
    </c:if>
    <c:if test="${(isFollowing != null) && (isFollowing)}">
        <li><a href="/pages/unsetsubscription?userid=${user.userId}">Stop follow</a></li>
    </c:if>
</ul>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>