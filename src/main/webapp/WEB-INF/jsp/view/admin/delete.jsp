<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website User Management</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        
        <h2>Delete Confirmation</h2>
        
        <p>Are you sure that you want to delete the user <c:out value="${username}"/>?</p>
        
        <div style="display: flex; flex-direction: row;">
            <c:url var="formUrl" value="/admin/delete"/>
            <form action="${formUrl}" method="post">
                <input type="submit" value="Confirm" />
                <input type="hidden" name="username" value="<c:out value="${username}"/>" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <a href="<c:url value="/admin" />">Back</a>
        </div>
    </body>
</html>