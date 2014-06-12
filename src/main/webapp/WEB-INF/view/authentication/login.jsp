<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

Please, login
<br/><br/>

<form action="login" method="post">
    <div>Login: <input name="login" type="text"></div>
    <div>Password: <input name="password" type="password"></div>
    <button type="submit" value="login">Login</button>
    <button type="reset">Clear</button>
</form>

<br/><br/>

Or <a href="<c:url value='/pages/register'/>">register</a>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>