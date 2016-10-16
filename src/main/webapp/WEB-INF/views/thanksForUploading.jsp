<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thanks for uploading files</title>
</head>
<body>
<h1>Thank you '<c:out value="${userName}"/>' for uploading files!</h1>
<c:forEach items="${uploadedFiles}" var="file">
    <div class="fileContent">
        <p>File name: ${file.originalName}</p>
        <ul>
            <c:forEach items="${file.textLines}" var="line">
                <li>${line}</li>
            </c:forEach>
        </ul>
        <hr/>
    </div>
</c:forEach>
</body>
</html>
