<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/WEB-INF/view/error/error.jsp" %>
<jsp:include page="/WEB-INF/view/header/header.jsp"></jsp:include>

<form method="post" action="${action}">
    <table>
        <tr>
            <td>Message</td>
            <td>
                <textarea required="true" name="postMessage"><c:if test="${post != null}">${post.postMessage}</c:if></textarea>
            </td>
        </tr>
    </table>

    <c:if test="${post != null}">
        <input name="postId" type="hidden" value="${post.postId}"/>
    </c:if>

    <button type="submit">Submit</button>
    <button type="reset">Reset</button>
    <button type="button">Cancel</button>
</form>

<jsp:include page="/WEB-INF/view/footer/footer.jsp"></jsp:include>