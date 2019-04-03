<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website User Management</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

        <h2>Edit User</h2>

        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>
        
        <c:url var="formUrl" value="/admin/edit"/>
        <form:form action="${formUrl}" method="POST" modelAttribute="userForm">
            <label>Username</label><br/>
            <span><c:out value="${userForm.username}"/></span>
            <form:input type="hidden" path="username" /><br/><br/>
            <form:label path="password">Password</form:label><br/>
            <form:input type="text" path="password" /><br/><br/>
            <form:label path="lecturer">Lecturer</form:label>
            <form:checkbox path="lecturer" /><br/><br/>
            <input type="submit" value="Save"/>
        </form:form>
    </body>
</html>
