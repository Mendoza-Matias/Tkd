package com.mmendoza.tkd.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mmendoza.tkd.core.model.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
