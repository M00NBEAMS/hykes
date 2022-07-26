<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "ctx" value="${pageContext.request.contextPath}" ></c:set>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>
    文件下载
</h1>
<hr>
<%--
    浏览器默认情况下会对文件进行解析，如果解析成功就直接打开，如果失败，默认下载
    例如图片、音频、有些格式的视频，可以直接打开
    例如压缩文件，.exe文件无法解析的，可以直接提供下载

    java方向的文件下载要满足以下三要素
    两个响应头和一个输入流
    1、目标文件的元数据类型
    2、设置响应头，且让能被浏览器解析的文件，不被解析，自动就会变成下载
    3、代表被下载文件的输入流


--%>

<a href="${ctx}/download">bbb.jpg</a><br>
</body>
</html>