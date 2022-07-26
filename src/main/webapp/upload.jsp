<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" ></c:set>
<html>
<head>
    <title>Title</title>
    <script>
        function changepic(obj){
            let s = URL.createObjectURL(obj.files[0]);
            document.querySelector(".img1").src= s;
            console.log(s)
        }
    </script>
</head>
<body>
<%--
    文件上传三要素
    1、提交方式必须是post
    2、必须要有文件上传项和name属性   <input type="file" name="upload1" >
    3、表单的enctype属性值，必须是多部分表单数据  enctype = "multipart/form-data"
--%>
<form action="${ctx}/upload" enctype="multipart/form-data" method="post">
    <input type="text" name="word" ><br>
    <input type="file" name="upload1" onchange="changepic(this)" accept="image/*,text/html,text/plain" >
    <img class="img1" src="" width="100" alt=""><br>
    <input type="submit" value="上传">
</form>
</body>
</html>
