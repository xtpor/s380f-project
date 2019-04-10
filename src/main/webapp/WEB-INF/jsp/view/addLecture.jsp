<!DOCTYPE html>
<html>
    <head>
        <title>Online Course Website Add Course</title>
    </head>
    <body>
        <h1>Online Course Website</h1>
        <%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

        <h2>Add Lecture</h2>

        <%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

        <form:form method="POST" enctype="multipart/form-data" modelAttribute="lectureForm">
            <form:label path="title">Tile</form:label><br/>
            <form:input type="text" path="title" /><br/><br/>
            
            <b>Attachments</b><br/>
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>