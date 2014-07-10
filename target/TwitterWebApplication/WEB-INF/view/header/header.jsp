<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/view/error/error.jsp" %>
<html>
<head>
    <title>Twitter Web Application</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/view/menu/menu.jsp"></jsp:include>

<c:if test="${errorMessage != null}">
<div>${errorMessage}</div>
</c:if>

