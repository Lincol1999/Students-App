package com.students.studentsApp.services.impls;

import com.students.studentsApp.entities.Student;
import com.students.studentsApp.repositories.StudentRepository;
import com.students.studentsApp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpls implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getPersonById(Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        boolean exits = studentRepository.existsById(student.getId());
        return exits ? studentRepository.save(student) : null ;
    }

    @Override
    public boolean deleteStudent(Integer id) {
        boolean delete = false;
        try {
            boolean exits = studentRepository.existsById(id);
            if (exits) studentRepository.deleteById(id);
            delete = true;
        }catch (RuntimeException e){
            e.printStackTrace();
            System.out.println(e);

        }
        return delete;
    }

    @Override
    public List<Student> getActiveStudent(boolean isActive) {
        return studentRepository.findByActive(isActive);
    }
}
