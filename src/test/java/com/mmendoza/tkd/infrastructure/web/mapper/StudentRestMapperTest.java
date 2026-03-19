package com.mmendoza.tkd.infrastructure.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mmendoza.tkd.core.model.Student;
import com.mmendoza.tkd.model.RegisterStudentRequest;
import com.mmendoza.tkd.model.StudentResponse;

public class StudentRestMapperTest {

    private final StudentRestMapper studentRestMapper = new StudentRestMapper();

    private RegisterStudentRequest registerStudentRequest;

    private Student student;

    @BeforeEach
    void setUp() {
        registerStudentRequest = new RegisterStudentRequest();
        registerStudentRequest.setFullName("John Doe");
        registerStudentRequest.setEmail("john.doe@example.com");
        registerStudentRequest.setPhone("123456789");

        student = Student.builder()
                .id(1)
                .fullName("John Doe")
                .email("john.doe@example.com")
                .phone("123456789")
                .build();
    }

    @Test
    void shouldReturnStudentWhenRegisterStudent() {
        Student student = studentRestMapper.toStudent(registerStudentRequest);
        assertEquals(registerStudentRequest.getFullName(), student.getFullName());
        assertEquals(registerStudentRequest.getEmail(), student.getEmail());
        assertEquals(registerStudentRequest.getPhone(), student.getPhone());
    }

    @Test
    void shouldReturnStudentResponseWhenStudent() {
        StudentResponse studentResponse = studentRestMapper.toStudentResponse(student);
        assertEquals(student.getId(), studentResponse.getId());
        assertEquals(student.getFullName(), studentResponse.getFullName());
        assertEquals(student.getEmail(), studentResponse.getEmail());
        assertEquals(student.getPhone(), studentResponse.getPhone());
    }

}
