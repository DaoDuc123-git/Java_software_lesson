package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import vn.edu.eaut.lab6.model.Student; 
import vn.edu.eaut.lab6.store.StudentStore; 
import java.io.IOException; 

@WebServlet("/students") 
public class StudentServlet extends HttpServlet { 
    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        request.setAttribute("students", StudentStore.findAll()); 
        request.getRequestDispatcher("/student-list.jsp").forward(request, response); 
    } 

    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        request.setCharacterEncoding("UTF-8"); 
        String id = request.getParameter("id"); 
        String name = request.getParameter("name"); 
        String className = request.getParameter("className"); 
        String email = request.getParameter("email"); 

        Student student = new Student(id, name, className, email); 
        StudentStore.add(student); 

        response.sendRedirect(request.getContextPath() + "/students"); 
    } 
}