package vn.edu.eaut.lab11.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.eaut.lab11.model.Course;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
            new Course("JAVA01", "Lập trình Java cơ bản", 3),
            new Course("JAVA02", "Lập trình Java nâng cao", 3),
            new Course("SPRING01", "Spring Boot Framework", 4),
            new Course("WEB01", "Thiết kế Web với HTML/CSS/JS", 2),
            new Course("DB01", "Cơ sở dữ liệu", 3)
        );
        model.addAttribute("courses", courses);
        return "courses";
    }
}