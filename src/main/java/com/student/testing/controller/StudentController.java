package com.student.testing.controller;

import com.student.testing.entity.Student;
import com.student.testing.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @PostMapping
    public ResponseEntity<Student> createStduent(@RequestBody Student student){
        return new ResponseEntity<>(this.studentService.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
        return new ResponseEntity<>(this.studentService.getStudentById(id),HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
        return new ResponseEntity<>(this.studentService.getAllStudent(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> udpateStudent(@PathVariable int id,@RequestBody Student student){
        return new ResponseEntity<>(this.studentService.updateStudent(id,student),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id){
        this.studentService.deleteStudent(id);
        return new ResponseEntity<>("User Deleted with id "+id,HttpStatus.OK);
    }



}
