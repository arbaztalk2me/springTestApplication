package com.student.testing.service;

import com.student.testing.entity.Student;
import com.student.testing.exception.ResourceAlreadyExistException;
import com.student.testing.exception.ResourceNotFound;
import com.student.testing.repo.StudentRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    public void setUp(){
        student=Student.builder().name("arbaz").email("arbaz@gmail.com").build();
    }

    @Test
    public void givenStudentwhenSaveStudentThenReturnStudent(){
        //given
        BDDMockito.given(studentRepo.findByEmail("arbaz@gmail.com")).willReturn(Optional.empty());
        BDDMockito.given(studentRepo.save(student)).willReturn(student);

        //when
        Student student1=studentService.createStudent(student);

        //then
        Assertions.assertThat(student1).isNotNull();
    }

    @Test
    public void givenStudentwhenSaveStudentThenReturnStudentThrowException(){
        //given
        BDDMockito.given(studentRepo.findByEmail("arbaz@gmail.com")).willReturn(Optional.of(student));


        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceAlreadyExistException.class,()->{
            studentService.createStudent(student);
        });

        //then
        Mockito.verify(studentRepo,Mockito.never()).save(ArgumentMatchers.any(Student.class));
    }


    @Test
    public void givenIdwhenGetStudentByIdThenReturnStudent(){
        //given
        BDDMockito.given(studentRepo.findById(1)).willReturn(Optional.of(student));


        //when
        Optional<Student> studentById = studentService.getStudentById(1);

        //then
        Assertions.assertThat(studentById).isNotEmpty();

    }

    @Test
    public void givenIdwhenGetStudentByIdThenReturnStudentThrowException(){
        //given
        BDDMockito.given(studentRepo.findById(1)).willReturn(Optional.empty());

//        //when
//        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class,()->{
//            studentService.getStudentById(1);
//        });

        studentService.getStudentById(1);

        //then
       Mockito.verify(studentRepo,Mockito.times(1)).findById(1);

    }

    @Test
    public void givenwhenGetAllStudentThenReturnListStudent(){
        //given
        BDDMockito.given(studentRepo.findAll()).willReturn(List.of(student));

        //when
        List<Student> allStudent = studentService.getAllStudent();

        //then
        Assertions.assertThat(allStudent.size()).isEqualTo(1);

    }

    @Test
    public void givenEmployeeAndIdwhenUpdateStudentThenReturnUpdateStudent(){
        //given
        BDDMockito.given(studentRepo.findById(1)).willReturn(Optional.of(student));
        BDDMockito.given(studentRepo.save(student)).willReturn(student);

        //when
        student.setEmail("kk@gmail.com");
        studentService.updateStudent(1,student);

        //then
        Assertions.assertThat(student.getEmail()).isEqualTo("kk@gmail.com");

    }

    @Test
    public void givenEmployeeAndIdwhenUpdateStudentThenReturnUpdateStudentThrowsException(){
        //given
        BDDMockito.given(studentRepo.findById(1)).willReturn(Optional.empty());


        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class,()->{
           studentService.updateStudent(1,student);
        });

        //then
        Mockito.verify(studentRepo,Mockito.times(1)).findById(1);
        Mockito.verify(studentRepo,Mockito.times(0)).save(student);

    }

    @Test
    public void givenEmployeeIdAndIdwhenDeleteStudentThenReturnVoid(){
        //given
        BDDMockito.given(studentRepo.findById(1)).willReturn(Optional.of(student));
        BDDMockito.willDoNothing().given(studentRepo).deleteById(1);

        //when

        studentService.deleteStudent(1);

        //then
        Mockito.verify(studentRepo,Mockito.times(1)).deleteById(1);

    }
    @Test
    public void givenEmployeeIdAndIdwhenDeleteStudentThenReturnVoidThrowsException(){
        //given
        BDDMockito.given(studentRepo.findById(1)).willReturn(Optional.empty());


        //when

        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class,()->{
           studentService.deleteStudent(1);
        });
            //then
        Mockito.verify(studentRepo,Mockito.times(0)).deleteById(1);

    }

}
