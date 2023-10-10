package com.student.testing.repo;

import com.student.testing.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    Optional<Student>findByEmail(String email);
}
