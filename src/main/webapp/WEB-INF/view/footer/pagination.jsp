<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
    <c:forEach begin="0" end="${(itemsCount % 10 == 0) ? (itemsCount / 10 - 1) : (itemsCount / 10)}"
               var="pageNum" varStatus="status">
        <c:set value="${(pageNum * 10 + 1)}" var="begin"></c:set>
        <c:choose>
            <c:when test="${!status.last}">
                <c:set value="${((pageNum + 1) * 10)}" var="end"></c:set>
            </c:when>
            <c:otherwise>
                <c:set value="${itemsCount}" var="end"></c:set>
            </c:otherwise>
        </c:choose>
       <li><a href="${destinationURL}&start=${begin}&end=${end}">${pageNum + 1}</a></li>

    </c:forEach>
</ul>