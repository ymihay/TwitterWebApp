<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 02.06.2014
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ooops!</title>
</head>
<body>
<h1>Ooops! An error occured</h1>
Exception: ${pageContext.exception.message} <br/>
Servlet: ${pageContext.errorData.servletName}<br/>
URI: ${pageContext.errorData.requestURI}<br/>
</body>
</html>
