package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import jakarta.servlet.http.HttpSession; 
import java.io.IOException; 

@WebServlet("/login") 
public class LoginServlet extends HttpServlet { 
    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        request.setCharacterEncoding("UTF-8"); 
        String username = request.getParameter("username"); 
        String password = request.getParameter("password"); 

        if ("admin".equals(username) && "123456".equals(password)) { 
            HttpSession session = request.getSession(); 
            session.setAttribute("username", username); 
            response.sendRedirect(request.getContextPath() + "/welcome.jsp"); 
        } else { 
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu"); 
            request.getRequestDispatcher("/login.jsp").forward(request, response); 
        } 
    } 
}