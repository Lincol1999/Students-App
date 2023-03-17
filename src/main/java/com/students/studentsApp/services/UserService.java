package com.students.studentsApp.services;

import com.students.studentsApp.entities.User;
import com.students.studentsApp.models.responses.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(UserModel model);
}
