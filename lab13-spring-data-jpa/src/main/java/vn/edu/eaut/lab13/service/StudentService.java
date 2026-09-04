package vn.edu.eaut.lab13.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab13.entity.Student;
import vn.edu.eaut.lab13.repository.StudentRepository;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() { return studentRepository.findAll(); }
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
    }
    public Student save(Student student) { return studentRepository.save(student); }
    public void deleteById(Long id) { studentRepository.deleteById(id); }

    // Bài 7: Tìm kiếm theo họ tên
    public List<Student> search(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return studentRepository.findByFullNameContainingIgnoreCase(keyword);
        }
        return findAll();
    }
}