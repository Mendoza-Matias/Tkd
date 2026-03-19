package com.mmendoza.tkd.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.Student;
import com.mmendoza.tkd.core.repository.IStudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private IStudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = Student.builder().id(1).fullName("John Doe").email("john.doe@example.com").phone("123456789").build();
    }

    @Test
    void shouldReturnStudentWhenRegisterStudent() {
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(false);
        when(studentRepository.existsByPhone(student.getPhone())).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);
        Integer id = studentService.registerStudent(student);
        assertEquals(student.getId(), id);
    }

    @Test
    void shouldThrowBussinesExceptionWhenEmailExists() {
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(true);
        BussinesException exception = assertThrows(BussinesException.class, () -> {
            studentService.registerStudent(student);
        });
        assertEquals("Datos ya registrados", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void shouldThrowBussinesExceptionWhenPhoneExists() {
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(false);
        when(studentRepository.existsByPhone(student.getPhone())).thenReturn(true);
        BussinesException exception = assertThrows(BussinesException.class, () -> {
            studentService.registerStudent(student);
        });
        assertEquals("Datos ya registrados", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
