<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

        <h2>Lectures</h2>
        <c:choose>
            <c:when test="${fn:length(lecture) == 0}">
                <i>There are no lecture.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${lecture}" var="entry">
                    <a href="<c:url value="/lecture/view/${entry.id}" />">
                        <c:out value="${entry.title}" />
                    </a>
                    <sec:authorize access="hasRole('ADMIN')">
                        <c:url var="deleteUrl" value="/lecture/delete">
                            <c:param name="lid" value="${entry.id}" />
                        </c:url>
                        [<a href="${deleteUrl}">Delete</a>]
                    </sec:authorize>
                    <br/>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <h2>Polls</h2>

        <sec:authorize access="hasRole('ADMIN')">
            <li><a href="<c:url value="/poll/add"/>">Add Poll</a></li>
        </sec:authorize>

        <ol>
            <c:forEach items="${polls}" var="polls">
                <c:url var="viewUrl" value="/poll/">
                    <c:param name="id" value="${polls.id}" />
                </c:url>
                <li>
                    <a href="${viewUrl}"><c:out value="${polls.question}"/></a>
                </li>
            </c:forEach>
        </ol>
    </body>
</html>
