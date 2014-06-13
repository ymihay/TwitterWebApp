<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/view/error/error.jsp" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

Click to <a href="<c:url value="/pages/logout"/>">log out</a>

<h1><a href="<c:url value="/pages/register"/>">Please register here </a></h1>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>