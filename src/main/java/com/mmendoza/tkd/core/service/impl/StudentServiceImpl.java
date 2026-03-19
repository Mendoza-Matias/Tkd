package com.mmendoza.tkd.core.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mmendoza.tkd.core.exception.BussinesException;
import com.mmendoza.tkd.core.model.Student;
import com.mmendoza.tkd.core.repository.IStudentRepository;
import com.mmendoza.tkd.core.service.IStudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;

    @Override
    public Integer registerStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new BussinesException("Datos ya registrados", HttpStatus.BAD_REQUEST);
        }
        if (studentRepository.existsByPhone(student.getPhone())) {
            throw new BussinesException("Datos ya registrados", HttpStatus.BAD_REQUEST);
        }
        return studentRepository.save(student).getId();
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
