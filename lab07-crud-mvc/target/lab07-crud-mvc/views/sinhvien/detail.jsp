<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi tiết sinh viên</title>
</head>
<body>
    <h2>Thông tin sinh viên</h2>
    <p><strong>ID:</strong> ${sv.id}</p>
    <p><strong>Mã SV:</strong> ${sv.maSinhVien}</p>
    <p><strong>Họ tên:</strong> ${sv.hoTen}</p>
    <p><strong>Email:</strong> ${sv.email}</p>
    <p><strong>Lớp:</strong> ${sv.lop}</p>
    <p><a href="${pageContext.request.contextPath}/sinh-vien">Quay lại danh sách</a></p>
</body>
</html>