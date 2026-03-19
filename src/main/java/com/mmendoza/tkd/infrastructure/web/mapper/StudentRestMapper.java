package com.mmendoza.tkd.infrastructure.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mmendoza.tkd.core.model.Student;
import com.mmendoza.tkd.model.RegisterStudentRequest;
import com.mmendoza.tkd.model.StudentResponse;

@Component
public class StudentRestMapper {

    public List<StudentResponse> toStudentResponse(List<Student> students) {
        return students.stream().map(this::toStudentResponse).collect(Collectors.toList());
    }

    public StudentResponse toStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFullName(student.getFullName());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        return response;
    }

    public Student toStudent(RegisterStudentRequest request) {
        return Student.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }
}
