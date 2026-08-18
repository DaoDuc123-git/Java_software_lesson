<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html> 
<head> 
    <title>Thêm sinh viên</title> 
</head> 
<body> 
    <h2>Thêm sinh viên</h2> 
    <form action="${pageContext.request.contextPath}/students" method="post"> 
        <label>Mã sinh viên:</label><br> 
        <input type="text" name="id"><br><br> 
        <label>Họ tên:</label><br> 
        <input type="text" name="name"><br><br> 
        <label>Lớp:</label><br> 
        <input type="text" name="className"><br><br> 
        <label>Email:</label><br> 
        <input type="email" name="email"><br><br> 
        <button type="submit">Lưu sinh viên</button> 
    </form> 
</body> 
</html>