package com.student.testing.service;

import com.student.testing.entity.Student;
import com.student.testing.exception.ResourceAlreadyExistException;
import com.student.testing.exception.ResourceNotFound;
import com.student.testing.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public Student createStudent(Student student) {
        if(this.studentRepo.findByEmail(student.getEmail()).isPresent()){
            throw new ResourceAlreadyExistException("Already Present "+student.getEmail());
        }
        return studentRepo.save(student);
    }

    @Override
    public Optional<Student> getStudentById(int id) {
        return this.studentRepo.findById(id);
    }

    @Override
    public List<Student> getAllStudent() {
        return this.studentRepo.findAll();
    }

    @Override
    public Student updateStudent(int id, Student student) {
        Student student1=this.studentRepo.findById(id).orElseThrow(()->new ResourceNotFound("Not Found Student with id "+id));
        student1.setEmail(student.getEmail());
        student1.setName(student.getName());
        return this.studentRepo.save(student1);
    }

    @Override
    public void deleteStudent(int id) {
        Student student1=this.studentRepo.findById(id).orElseThrow(()->new ResourceNotFound("Not Found Student with id "+id));
        this.studentRepo.deleteById(id);
    }
}
