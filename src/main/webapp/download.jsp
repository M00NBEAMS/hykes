<%--
  Created by IntelliJ IDEA.
  User: MK
  Date: 2022-07-25
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "ctx" value="${pageContext.request.contextPath}" ></c:set>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${ctx}/getFilePath" method="post">
    <input type="text" name="dirpath" >
    <input type="submit" value="显示文件">
</form>
<c:if test="${not empty list}">
    <ul>
        <c:forEach items="${list}" var="entry" >
            <li><a href="${ctx}/download2?path=${entry.key}">${entry.value}</a></li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
