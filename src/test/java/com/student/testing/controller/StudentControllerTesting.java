package com.student.testing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.testing.entity.Student;
import com.student.testing.service.StudentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class StudentControllerTesting {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
   private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenStudentWhenSaveStudentThenReturnStudent() throws Exception {
        //given
        Student student=Student.builder().name("arbaz").email("arbaz@gmail.com").build();
        BDDMockito.given(studentService.createStudent(ArgumentMatchers.any(Student.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student))
        );

        //then
        perform.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(student.getName())));
    }


    @Test
    public void givenListOfStudent_whenGetAllStudent_returnListStudent() throws Exception {
        //given
        Student student=Student.builder().name("arbaz").email("arbaz@gmail.com").build();
        List<Student> list=new ArrayList<>();
        list.add(student);
        BDDMockito.given(studentService.getAllStudent())
                .willReturn(list);
        //when
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(list.size())));
    }

    @Test
    public void givenStudentId_whenGetStudentById_returnStudent() throws Exception {
        //given
        int studentId=1;
        Student student=Student.builder().name("arbaz").email("arbaz@gmail.com").build();
        BDDMockito.given(studentService.getStudentById(studentId))
                .willReturn(student);

        //when
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/students/{id}",studentId));


        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(student.getName())));

    }

    @Test
    public void givenStudentId_whenGetStudentById_returnStudentNegative () throws Exception {
        //given
        int studentId=1;
        BDDMockito.given(studentService.getStudentById(studentId))
                .willReturn(null);

        //when
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/students/{id}",studentId));


        //then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_returnUpdatedEmploye() throws Exception {
        //given
        int studentId=1;
        Student student=Student.builder().name("arbaz").email("arbaz@gmail.com").build();
        BDDMockito.given(studentService.getStudentById(studentId))
                .willReturn(student);

        BDDMockito.given(studentService.updateStudent(1,ArgumentMatchers.any(Student.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/api/students/{id}",studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(student.getName())));
    }

    @Test
    public void givenStuddentId_whenDeleteStudent_returnMsg() throws Exception{
        int studentId=1;
        //given
        BDDMockito.willDoNothing().given(studentService).deleteStudent(studentId);
        //when
        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/{id}",studentId));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }




}
