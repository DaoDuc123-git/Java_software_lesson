<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lab 7 - CRUD MVC</title>
</head>
<body>
    <h2>Lab 7 - CRUD bằng Servlet + JSP, dùng MVC đơn giản</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/sinh-vien">Quản lý sinh viên</a></li>
        <li><a href="${pageContext.request.contextPath}/sach">Quản lý sách</a></li>
        <li><a href="${pageContext.request.contextPath}/san-pham">Quản lý sản phẩm</a></li>
        <li><a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a></li>
    </ul>
</body>
</html>