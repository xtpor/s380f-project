<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website User Management</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

        <h2>View Poll</h2>

        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

        <h3><c:out value="${poll.question}" /></h3>

        <sec:authorize access="hasRole('ADMIN')">
            <c:url var="deleteUrl" value="/poll/delete">
                <c:param name="id" value="${poll.id}" />
            </c:url>
            [<a href="${deleteUrl}">Delete Poll</a>]
            <br> <br>
        </sec:authorize>


        <c:url var="voteUrl" value="/poll/vote"/>

        <c:forEach var="option" items="${options}">

            <form action="${voteUrl}" method="POST" style="display: inline; margin-right: 20px">
                <c:if test="${selectedOption == -1}">
                    <input type="submit" value="Vote" style="width: 25em"/>
                </c:if>
                <c:if test="${selectedOption != -1}">
                    <c:if test="${selectedOption == option.no}">
                        <input type="submit" value="You have voted for this option" disabled style="width: 20em">
                    </c:if>
                    <c:if test="${selectedOption != option.no}">
                        <input type="submit" value="Re-vote" style="width: 20em"/>
                    </c:if>
                </c:if>
                <input type="hidden" name="no" value="${option.no}">
                <input type="hidden" name="pollId" value="${option.pollId}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            ${option.content}

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

            <c:choose>
                <c:when test="${counts.get(option.no) != null}">
                    <c:out value="${counts.get(option.no)}" />
                </c:when>
                <c:otherwise>
                    0
                </c:otherwise>
            </c:choose>

            <br><br>

        </c:forEach>

        <hr>

        <ol>
        <c:forEach var="comment" items="${comments}">
            <jsp:useBean id="dateValue" class="java.util.Date"/>
            <jsp:setProperty name="dateValue" property="time" value="${comment.postTime * 1000}"/>
            <br>

            Date: <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
            <sec:authorize access="hasRole('ADMIN')">
                <c:url var="deleteCommentUrl" value="/poll/delete-comment">

                </c:url>
                <form action="${deleteCommentUrl}" method="post" style="display: inline">
                    <input type="submit" value="Delete this comment">
                    <input type="hidden" name="commentId" value="${comment.id}">
                    <input type="hidden" name="id" value="${comment.pollId}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </sec:authorize>
            <br><br>

            Name: <c:out value="${comment.username}" /> <br><br><br>
            Comment: <c:out value="${comment.content}" /> <br>
            <br>
            <hr>
        </c:forEach>
        </ol>

        <br>
        <h3>Comment</h3>

        <textarea name="content" form="commentForm" cols="100%" rows="10"></textarea>

        <c:url var="commentUrl" value="/poll/comment"/>
        <form id="commentForm" action="${commentUrl}" method="POST">
            <input type="submit" value="Comment" />
            <input type="hidden" name="pollId" value="${poll.id}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </body>
</html>
