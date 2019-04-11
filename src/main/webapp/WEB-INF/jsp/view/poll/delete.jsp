<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website Poll Management</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        
        <h2>Delete Confirmation</h2>
        
        <p>Are you sure that you want to delete the poll <c:out value="${question}"/>?</p>
        
        <div style="display: flex; flex-direction: row;">
            <c:url var="formUrl" value="/poll/delete"/>
            <form action="${formUrl}" method="post">
                <input type="submit" value="Confirm" />
                <input type="hidden" name="id" value="<c:out value="${id}"/>" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <a href="<c:url value="/" />">Back</a>
        </div>
    </body>
</html>