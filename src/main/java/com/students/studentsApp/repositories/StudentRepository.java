package com.students.studentsApp.repositories;

import com.students.studentsApp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByActive(boolean active);
    
}
