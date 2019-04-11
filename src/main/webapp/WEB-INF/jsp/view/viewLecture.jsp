<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website</title>
    </head>
    <h1>Online Course Website</h1>
    <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>
    <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

    <b>Lecture #<c:out value="${lecture.id}" /> - <c:out value="${lecture.title}" /></b> <br/><br/>

    <b>Attachment</b> <br/>
    <c:choose>
        <c:when test="${fn:length(attachments) == 0}">
            <i>There are no attachment.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${attachments}" var="attachment">
                <a href="<c:url value="/lecture/${lecture.id}/attachment/${attachment.aid}" />">
                    <c:out value="${attachment.name}" />
                </a>
                <sec:authorize access="hasRole('ADMIN')">
                    <c:url var="deleteUrl" value="/lecture/attachment/delete">
                        <c:param name="aid" value="${attachment.aid}" />
                        <c:param name="lid" value="${lecture.id}" />
                    </c:url>
                    [<a href="${deleteUrl}">Delete</a>]
                </sec:authorize>
                <br/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <br/>

    <sec:authorize access="hasRole('ADMIN')">
        <b>Add attachment</b> <br/>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="form">
            <input type="file" name="attachments" multiple="multiple"/><br/>
            <input type="submit" value="Upload"/>
        </form:form>
        <br/> 
    </sec:authorize>

    <b>Comment</b> <br/>
    <c:forEach items="${comments}" var="comment">
        <c:out value="${comment.name}" /> : 
        <c:out value="${comment.comment}" />
        <sec:authorize access="hasRole('ADMIN')">
            <c:url var="deleteUrl" value="/lecture/comment/delete">
                <c:param name="cid" value="${comment.cid}" />
                <c:param name="lid" value="${lecture.id}" />
            </c:url>
            [<a href="${deleteUrl}">Delete</a>]
        </sec:authorize>
        <br/>
    </c:forEach>

    <form:form method="POST" enctype="multipart/form-data" modelAttribute="commentForm">
        <input type="hidden" name="name" value="<sec:authentication property="principal.username" />"/>
        <input type="hidden" name="id" value="${lecture.id}"/>

        <form:input type="text" path="comment" /><br/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Comment"/>
    </form:form>

    <br/>
    <a href="<c:url value="/"/>">Return to menu</a>

</html>
