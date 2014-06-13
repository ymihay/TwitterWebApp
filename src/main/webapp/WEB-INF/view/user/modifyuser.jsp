<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/view/error/error.jsp" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

<h1>Profile info</h1>

<form method="post" action="${action}">
    <table>
        <tr>
            <td>First name</td>
            <td><input type="text" name="firstName" value="${user.firstName}"/></td>
        </tr>
        <tr>
            <td>Patronymic</td>
            <td><input type="text" name="patronymic" value="${user.patronymic}"/></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><input type="text" name="lastName" value="${user.lastName}"/> <br/></td>
        </tr>
        <tr>
            <td>Login</td>
            <td><input type="text" name="login" required="true" ${(user.login != null) ? " disabled " : ""}
                       value="${user.login}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" required="true" value="${user.password}"/></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td><input type="date" dataformatas="yyyy-mm-dd hh:mm:ss" required="true"
                       name="birthdate" value="${user.birthdate}"/></td>
        </tr>
        <tr>
            <td>Sex</td>
            <td>
                <select name="sexId">
                    <option value="">None</option>
                    <c:forEach var="currSex" items="${sex}">
                        <option value="${currSex.sexId}" ${currSex.sexId == user.sex.sexId ? " selected " : ""}>
                            ${currSex.sexName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Country</td>
            <td>
                <select name="countryId">
                    <option value="">None</option>
                    <c:forEach var="country" items="${countries}">
                        <option value="${country.countryId}"
                        ${country.countryId == user.country.countryId ? " selected " : ""}>
                        ${country.countryName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <button type="submit">Submit</button>
    <button type="reset">Reset</button>
    <button type="button">Cancel</button>
</form>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>