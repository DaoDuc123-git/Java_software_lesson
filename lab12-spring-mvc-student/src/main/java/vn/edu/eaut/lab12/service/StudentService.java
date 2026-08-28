package vn.edu.eaut.lab12.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    public StudentService() {
        // Khởi tạo dữ liệu mẫu
        save(new Student(null, "SV00001", "Nguyễn Văn A", "ana@gmail.com", "IT01"));
        save(new Student(null, "SV00002", "Trần Thị B", "theb@gmail.com", "IT02"));
    }

    public List<Student> findAll() {
        return students;
    }

    public Optional<Student> findById(Long id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return students;
        }
        return students.stream()
                .filter(s -> s.getFullName().toLowerCase().contains(keyword.trim().toLowerCase()))
                .toList();
    }

    public boolean isStudentCodeExists(String studentCode, Long currentId) {
        return students.stream().anyMatch(s -> 
            s.getStudentCode().equalsIgnoreCase(studentCode) && !s.getId().equals(currentId)
        );
    }

    public void save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
            students.add(student);
        } else {
            findById(student.getId()).ifPresent(existing -> {
                existing.setStudentCode(student.getStudentCode());
                existing.setFullName(student.getFullName());
                existing.setEmail(student.getEmail());
                existing.setClassName(student.getClassName());
            });
        }
    }

    public void deleteById(Long id) {
        students.removeIf(s -> s.getId().equals(id));
    }
}