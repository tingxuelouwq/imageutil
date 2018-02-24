<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 2017/7/7
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
    <base href="">
</head>
<body>
    <div align="center">
        <h2>图片上传</h2>
        <form action="${pageContext.request.contextPath}/watermark" enctype="multipart/form-data" method="post">
            <h2>请上传图片</h2>
            <input type="file" name="image">
            <input type="submit" value="上传图片">
        </form>
    </div>

    <div align="center">
        <h2>图片批量上传</h2>
        <form action="${pageContext.request.contextPath}/multi-watermark" enctype="multipart/form-data" method="post">
            <h2>请上传图片</h2>
            <input type="file" name="images"/>
            <br />
            <input type="file" name="images"/>
            <br />
            <input type="file" name="images"/>
            <br />
            <input type="file" name="images"/>
            <br />
            <input type="file" name="images"/>
            <br />
            <input type="submit" value="上传图片" />
        </form>
    </div>
</body>
</html>
</input>