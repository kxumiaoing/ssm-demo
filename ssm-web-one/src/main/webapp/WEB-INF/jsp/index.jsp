<%--
  Created by IntelliJ IDEA.
  User: xumiao
  Date: 4/20/18
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
    <table width="80%" align="center" border="1">
        <thead>
            <tr align="right">
                <td colspan="2">
                    <a href="/user/prepareAdd">添加</a>
                </td>
                <td colspan="2">
                    <a href="/user/list/${page.currentPage - 1}" <c:if test="${1 == page.currentPage}">style="display: none"</c:if>>上一页</a>&nbsp;
                    <a href="/user/list/${page.currentPage + 1}" <c:if test="${page.totalPages == page.currentPage}">style="display: none"</c:if>>下一页</a>&nbsp;
                    ${page.currentPage}/${page.totalPages}页
                </td>
            </tr>
            <tr align="center">
                <td>编号</td>
                <td>姓名</td>
                <td>年龄</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr align="center">
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>
                        <a href="/user/prepareUpdate/${user.id}">更新</a>
                        <a href="/user/delete/${user.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
