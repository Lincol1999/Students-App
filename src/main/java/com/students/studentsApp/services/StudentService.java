package com.students.studentsApp.services;

import com.students.studentsApp.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getStudents();

    Optional<Student> getPersonById(Integer id);

    Student createStudent(Student student);

    Student updateStudent(Student student);

    boolean deleteStudent(Integer id);

    List<Student> getActiveStudent(boolean isActive);
}
