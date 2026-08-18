<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<html> 
<head> 
    <title>Danh sách sinh viên</title> 
</head> 
<body> 
    <h2>Danh sách sinh viên</h2> 
    <a href="${pageContext.request.contextPath}/student-form.jsp">Thêm sinh viên</a> 
    <br><br> 
    <table border="1" cellpadding="8" cellspacing="0"> 
        <tr> 
            <th>Mã SV</th> 
            <th>Họ tên</th> 
            <th>Lớp</th> 
            <th>Email</th> 
        </tr> 
        <c:forEach var="sv" items="${students}"> 
            <tr> 
                <td>${sv.id}</td> 
                <td>${sv.name}</td> 
                <td>${sv.className}</td> 
                <td>${sv.email}</td> 
            </tr> 
        </c:forEach> 
    </table> 
</body> 
</html>