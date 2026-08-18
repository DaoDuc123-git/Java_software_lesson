package vn.edu.eaut.lab6.store;

import java.util.ArrayList;
import java.util.List;
import vn.edu.eaut.lab6.model.Student; 

public class StudentStore { 
    private static final List<Student> students = new ArrayList<>(); 

    static { 
        students.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com")); 
        students.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com")); 
    } 

    public static List<Student> findAll() { 
        return students; 
    } 

    public static void add(Student student) { 
        students.add(student); 
    } 
}