<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website User Management</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

        <h2>Add User</h2>

        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

        <c:url var="formUrl" value="/admin/add"/>
        <form action="${formUrl}" method="POST">
            <label name="username">Username</label><br/>
            <input type="text" name="username" /><br/><br/>
            <label name="password">Password</label><br/>
            <input type="password" name="password" /><br/><br/>
            <label name="lecturer">Lecturer</label>
            <input type="checkbox" name="lecturer" /><br/><br/>
            
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
