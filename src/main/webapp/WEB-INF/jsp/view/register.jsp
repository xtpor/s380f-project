<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website Register</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>
        
        <h2>Register</h2>
            
        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>
        
        <form method="POST">
            <label for="username">Username:</label><br/>
            <input type="text" id="username" name="username" /><br/><br/>
            <label for="password">Password:</label><br/>
            <input type="password" id="password" name="password" /><br/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Register"/>
        </form>
    </body>
</html>