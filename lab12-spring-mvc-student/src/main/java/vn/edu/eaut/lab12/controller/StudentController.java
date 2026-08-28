package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Bài 3 & Bài 9: Danh sách & Tìm kiếm
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.searchByName(keyword));
        model.addAttribute("keyword", keyword);
        return "students/list";
    }

    // Bài 4: Form tạo mới
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    // Bài 7: Form sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    return "students/form";
                })
                .orElse("redirect:/students");
    }

    // Bài 6: Xem chi tiết
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    return "students/detail";
                })
                .orElse("redirect:/students");
    }

    // Bài 4, 5, 10: Xử lý lưu & Validation trùng mã
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result) {
        // Validation Bài 10: Kiểm tra trùng mã sinh viên
        if (studentService.isStudentCodeExists(student.getStudentCode(), student.getId())) {
            result.addError(new FieldError("student", "studentCode", "Mã sinh viên đã tồn tại trong hệ thống"));
        }

        if (result.hasErrors()) {
            return "students/form";
        }

        studentService.save(student);
        return "redirect:/students";
    }

    // Bài 8: Xóa sinh viên
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }
}