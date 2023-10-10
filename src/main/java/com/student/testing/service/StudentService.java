package com.student.testing.service;

import com.student.testing.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);
    Optional<Student> getStudentById(int id);
    List<Student> getAllStudent();
    Student updateStudent(int id,Student student);
    void deleteStudent(int id);
}
