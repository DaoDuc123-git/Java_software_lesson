package vn.edu.eaut.lab14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String listCourses() {
        return "courses/list";
    }
}