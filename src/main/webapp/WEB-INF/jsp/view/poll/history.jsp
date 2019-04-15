<!DOCTYPE html>
<html>
<head>
    <title>Online Course Voting history</title>
</head>
<body>
<h1>Online Course Website</h1>
<%@include file="/WEB-INF/jsp/fragments/navbar.jspf" %>

<h2>Voting history</h2>

<%@include file="/WEB-INF/jsp/fragments/status.jspf" %>

<br>

<table style="border-collapse: collapse">
    <tr style="border-bottom: 1.5px solid black; height: 3em;">
        <td width="10%">
            Date
        </td>
        <td width="45%" style="padding-left: 15px">
            Poll
        </td>
        <td width="45%">
            Your choice
        </td>
    </tr>
    <c:forEach items="${responses}" var="response">
        <jsp:useBean id="dateValue" class="java.util.Date"/>
        <jsp:setProperty name="dateValue" property="time" value="${response.postTime * 1000}"/>
        <c:url var="pollUrl" value="/poll/">
            <c:param name="id" value="${response.pollId}"/>
        </c:url>

        <tr style="border-bottom: 1.5px solid black; height: 3em; width: 80%">
            <td>
                <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
            </td>
            <td>
                <a href="${pollUrl}" style="margin-left: 15px"><c:out value="${response.question}"/></a>
            </td>
            <td>
                <c:out value="${response.content}"/>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
