package com.mmendoza.tkd.core.service;

import java.util.List;

import com.mmendoza.tkd.core.model.Student;

public interface IStudentService {

    List<Student> getAllStudents();

    Integer registerStudent(Student student);
}
