package com.student.testing.repo;

import com.student.testing.entity.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepoTest {
    @Autowired
    private StudentRepo studentRepo;

    private Student student;

    @BeforeEach
    private void setUp(){
        student=Student.builder().email("arbaz@gmail.com")
                .name("arbaz").build();
    }


    @Test
    public void givenEmployeeWhenSaveThenReturnEmployee(){
        //given
        //when
        Student save = studentRepo.save(student);
        //then
        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeWhenFindEmployeeThenReturnEmployee(){
        //given
        studentRepo.save(student);

        //when
        Optional<Student> st=studentRepo.findById(1);

        //then
        Assertions.assertThat(st).isNotNull();


    }
    @Test
    public void givenListOfEmployeeWhenFindAllEmployeeThenReturnListOfEmployee(){
        //given
        studentRepo.save(student);
        studentRepo.save(Student.builder().email("jabaz@gmail.com").name("jabaz").build());
        //when
        List<Student> list=studentRepo.findAll();

        //then
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void givenEmailWhenFindEmployeeThenReturnEmployee(){
        //given
        studentRepo.save(student);

        //when
        Student list=studentRepo.findByEmail("arbaz@gmail.com").get();

        //then
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.getEmail()).isEqualTo("arbaz@gmail.com");
    }

    @Test
    void givenStudent_whenUpdate_thenReturnStudent() {
        //given
        studentRepo.save(student);
        //when
        Student student1 = studentRepo.findById(student.getId()).get();
        student1.setName("jabaz");
        student1.setEmail("jabaz@gmail.com");
        Student save = studentRepo.save(student1);
        //then
        assertThat(save.getEmail()).isEqualTo("jabaz@gmail.com");
        assertThat(save.getName()).isEqualTo("jabaz");

    }

    @Test
    void givenStudent_whenDelete_thenRemoveStudent(){
        //given
        studentRepo.save(student);
        //when
        studentRepo.deleteById(student.getId());
        Optional<Student> st=studentRepo.findById(student.getId());

        //then
        Assertions.assertThat(st).isEmpty();
    }

}
