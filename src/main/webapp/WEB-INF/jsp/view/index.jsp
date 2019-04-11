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
        <p>list of lectures here TODO</p>
        
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
