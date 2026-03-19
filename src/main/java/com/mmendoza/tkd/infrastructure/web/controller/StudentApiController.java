package com.mmendoza.tkd.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.mmendoza.tkd.api.StudentApi;
import com.mmendoza.tkd.core.model.Student;
import com.mmendoza.tkd.core.service.IStudentService;
import com.mmendoza.tkd.infrastructure.web.mapper.StudentRestMapper;
import com.mmendoza.tkd.model.RegisterStudentRequest;
import com.mmendoza.tkd.model.StudentResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StudentApiController implements StudentApi {

    private final IStudentService studentService;
    private final StudentRestMapper studentRestMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Integer> registerStudent(RegisterStudentRequest registerStudentRequest) {
        Integer id = studentService.registerStudent(studentRestMapper.toStudent(registerStudentRequest));
        return ResponseEntity.ok(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(studentRestMapper.toStudentResponse(students));
    }
}