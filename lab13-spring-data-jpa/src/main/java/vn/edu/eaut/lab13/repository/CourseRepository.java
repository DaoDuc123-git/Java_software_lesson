package vn.edu.eaut.lab13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eaut.lab13.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}