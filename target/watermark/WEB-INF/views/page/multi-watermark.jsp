<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 2017/8/3
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getServletContext().getContextPath();
%>
<html>
<head>
    <title>处理结果</title>
</head>
<body>
    <c:set var="ctx" value="<%=contextPath%>"/>
    <h2 align="center">操作结果</h2>
    <table width="100%" align="center">
        <c:forEach var="pictureInfo" items="${pictureInfos}">
            <tr>
                <td width="50%" align="center">
                    <img src="${ctx}/${pictureInfo.imageUrl}" width="500"/>
                </td>
                <td width="50%" align="center">
                    <img src="${ctx}/${pictureInfo.logoImageUrl}" width="500"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <hr/>
    <div align="center">
        <a href="${ctx}/index.jsp">返回</a>
    </div>
</body>
</html>
