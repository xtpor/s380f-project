<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website User Management</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

        <h2>User Management</h2>

        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>
        
        <a href="<c:url value="/admin/add" />">Add user</a>

        <h3>List of students</h3>
        <ol>
           <c:forEach items="${students}" var="student">
                <c:url var="editUrl" value="/admin/edit">
                    <c:param name="username" value="${student.username}" />
                </c:url>
                <c:url var="deleteUrl" value="/admin/delete">
                    <c:param name="username" value="${student.username}" />
                </c:url>
               <li>
                   <c:out value="${student.username}"/>
                   [<a href="${editUrl}">Edit</a>]
                   [<a href="${deleteUrl}">Delete</a>]
               </li>
            </c:forEach>
        </ol>

        <h3>List of lecturers</h3>
        <ol>
           <c:forEach items="${lecturers}" var="lecturer">
                <c:url var="editUrl" value="/admin/edit">
                    <c:param name="username" value="${lecturer.username}" />
                </c:url>
                <c:url var="deleteUrl" value="/admin/delete">
                    <c:param name="username" value="${lecturer.username}" />
                </c:url>
               <li>
                   <c:out value="${lecturer.username}"/>
                   [<a href="${editUrl}">Edit</a>]
                   [<a href="${deleteUrl}">Delete</a>]
               </li>
            </c:forEach>
        </ol>
    </body>
</html>
