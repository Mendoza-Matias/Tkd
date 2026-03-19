package com.mmendoza.tkd.infrastructure.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mmendoza.tkd.core.model.Student;
import com.mmendoza.tkd.core.service.IStudentService;
import com.mmendoza.tkd.infrastructure.web.mapper.StudentRestMapper;
import com.mmendoza.tkd.model.RegisterStudentRequest;
import com.mmendoza.tkd.model.StudentResponse;

@ExtendWith(MockitoExtension.class)
public class StudentApiControllerTest {

    @Mock
    private IStudentService studentService;

    @Mock
    private StudentRestMapper studentRestMapper;

    @InjectMocks
    private StudentApiController studentApiController;

    private RegisterStudentRequest registerStudentRequest;

    private Student student;

    private StudentResponse studentResponse;

    @BeforeEach
    void setup() {
        registerStudentRequest = new RegisterStudentRequest();
        registerStudentRequest.fullName("Test");
        registerStudentRequest.email("test@example.com");
        registerStudentRequest.phone("+549111234567");

        student = Student.builder()
                .id(1)
                .fullName("Test")
                .email("test@example.com")
                .phone("+549111234567")
                .build();

        studentResponse = new StudentResponse();
        studentResponse.id(1);
        studentResponse.fullName("Test");
        studentResponse.email("test@example.com");
        studentResponse.phone("+549111234567");
    }

    @Test
    void shouldRegisterStudent() {
        when(studentRestMapper.toStudent(registerStudentRequest)).thenReturn(student);
        when(studentService.registerStudent(any(Student.class))).thenReturn(1);

        ResponseEntity<Integer> response = studentApiController.registerStudent(registerStudentRequest);

        verify(studentService, times(1)).registerStudent(any(Student.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    void shouldGetAllStudents() {
        when(studentService.getAllStudents()).thenReturn(List.of(student));

        when(studentRestMapper.toStudentResponse(anyList())).thenReturn(List.of(
                studentResponse));

        ResponseEntity<List<StudentResponse>> response = studentApiController.getAllStudents();

        verify(studentService, times(1)).getAllStudents();
        verify(studentRestMapper, times(1)).toStudentResponse(anyList());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(student.getId(), response.getBody().get(0).getId());
        assertEquals(student.getFullName(), response.getBody().get(0).getFullName());
        assertEquals(student.getEmail(), response.getBody().get(0).getEmail());
        assertEquals(student.getPhone(), response.getBody().get(0).getPhone());
    }
}
