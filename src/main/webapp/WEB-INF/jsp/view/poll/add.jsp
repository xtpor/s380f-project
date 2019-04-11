<!DOCTYPE html>
<html>
<head>
    <title>Online Course Website User Management</title>
</head>
<body>
<h1>Online Course Website</h1>
<%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

<h2>Add Poll</h2>

<%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

<c:url var="formUrl" value="/poll/add"/>
<form action="${formUrl}" method="POST">
    <label name="question">Poll Question</label><br/>
    <input type="text" name="question" value="" /><br/><br/>

    <label name="optionList[0].content">Choice 1</label>
    <input type="text" name="optionList[0].content" /><br/><br/>

    <label name="optionList[1].content">Choice 2</label>
    <input type="text" name="optionList[1].content" /><br/><br/>

    <label name="optionList[2].content">Choice 3</label>
    <input type="text" name="optionList[2].content" /><br/><br/>

    <label name="optionList[3].content">Choice 4</label>
    <input type="text" name="optionList[3].content" /><br/><br/>

    <input type="hidden" name="optionList[0].no" value="1"/>
    <input type="hidden" name="optionList[1].no" value="2"/>
    <input type="hidden" name="optionList[2].no" value="3"/>
    <input type="hidden" name="optionList[3].no" value="4"/>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
