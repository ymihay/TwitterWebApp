<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/view/error/error.jsp" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

Please, login
<br/><br/>

<form action="login" method="post">
    <div>Login: <input id="username" name="username" type="text"></div>
    <div>Password: <input id="password" name="password" type="password"></div>
    <div>Remember me: <input type="checkbox" name="_spring_security_remember_me"></div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit" value="login">Login</button>
    <button type="reset">Clear</button>
</form>

<br/><br/>

Or <a href="<c:url value='/pages/register'/>">register</a>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>