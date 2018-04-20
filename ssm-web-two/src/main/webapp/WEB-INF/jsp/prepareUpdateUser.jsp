<%--
  Created by IntelliJ IDEA.
  User: xumiao
  Date: 4/20/18
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
    <form action="/user/update" method="post">
        姓  名：<input name="name" value="${user.name}" type="text"/><br/>
        年  龄：<input name="age" value="${user.age}" type="text"/><br/>
        <input name="id" value="${user.id}" type="hidden"/>
        <input value="提交" type="submit"/>
        <input value="重置" type="reset"/>
    </form>
</body>
</html>
