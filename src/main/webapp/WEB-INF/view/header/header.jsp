<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Twitter Web Application</title>
</head>
<body>

<jsp:include page="/WEB-INF/view/menu/menu.jsp"></jsp:include>

<c:if test="${errorMessage != null}"><div>${errorMessage}</div></c:if>
