<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>
    <h2>Đăng nhập hệ thống</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <p>Tài khoản: <input type="text" name="username" required></p>
        <p>Mật khẩu: <input type="password" name="password" required></p>
        <button type="submit">Đăng nhập</button>
    </form>
</body>
</html>