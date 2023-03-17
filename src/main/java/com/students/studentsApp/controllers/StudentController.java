package com.students.studentsApp.controllers;

import com.students.studentsApp.entities.Student;
import com.students.studentsApp.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/students/v1")
public class StudentController {

    private static final String ACTIVE = "Active";

    private static final String INACTIVE = "Inactive";

    @Autowired
    private StudentService studentService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "")
    ResponseEntity<List<Student>> getStudents () {

        try {
            List<Student> students = studentService.getStudents();

            if (students.isEmpty()){
                log.warn("Student no content");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            log.info("Student found");
            return new ResponseEntity<>(students, HttpStatus.OK);

        } catch (RuntimeException e) {
            log.error("Error while getting students");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    ResponseEntity<Student> getStudentById (@PathVariable Integer id) {
        try{
            Optional<Student> student = studentService.getPersonById(id);

            if (student.isPresent()){
                log.info("Student {} found", id);
                return new ResponseEntity<>(student.get(), HttpStatus.OK);
            }else{
                log.warn("Student {} no found", id );
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch (RuntimeException e) {
            log.error("Error while getting student {}", id);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "")
    ResponseEntity<Student> createStudent(@RequestBody Student student){
        try{
            Student newStudent = studentService.createStudent(student);
            log.info("Student Created");
            return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
        }catch (RuntimeException e){
            log.error("Error while creating student");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "")
    ResponseEntity<Student> updateStudent(@RequestBody Student student) {

        try{
            Student updateStudent = studentService.updateStudent(student);
            log.info("Student {} update", student.getId());
            return new ResponseEntity<>(updateStudent, HttpStatus.OK);
        }catch (RuntimeException e) {
            log.error("Error while updating student {}", student.getId());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Boolean> deleteStudent (@PathVariable Integer id) {
        try{
            boolean deleted = studentService.deleteStudent(id);
            log.info("Student {} deleted", id );
            return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            log.error("Error while deleting student {}", id);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/actives/{isActive}")
    ResponseEntity<List<Student>> getActiveStudents (@PathVariable boolean isActive) {
        try{
            List<Student> activeStudent = studentService.getActiveStudent(isActive);
            if (activeStudent.isEmpty()) {
                log.warn("{} student not found", isActive ? ACTIVE : INACTIVE);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            log.info("{} student found", isActive ? ACTIVE : INACTIVE);
            return new ResponseEntity<>(activeStudent, HttpStatus.OK);
        }catch (RuntimeException e){
            log.error("Error while getting {} student", isActive ? ACTIVE : INACTIVE);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



