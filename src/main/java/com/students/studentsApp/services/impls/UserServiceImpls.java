package com.students.studentsApp.services.impls;

import com.students.studentsApp.entities.Role;
import com.students.studentsApp.entities.User;
import com.students.studentsApp.models.UserModalDetails;
import com.students.studentsApp.models.UserModel;
import com.students.studentsApp.repositories.RoleRepository;
import com.students.studentsApp.repositories.UserRepository;
import com.students.studentsApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpls implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder () {
        return  new BCryptPasswordEncoder();
    }

    @Override
    public User register(UserModel model) {

        String roleName = "ROLE_USER";

        Role role = new Role(roleName);

        if (roleRepository.existsByRoleName(roleName)) {
                role = roleRepository.findByRoleName(roleName).get();
        }

        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(passwordEncoder().encode(model.getPassword()));
        user.setEmail(model.getEmail());
        user.setFirstname(model.getFirstname());
        user.setLastname(model.getLastname());
        user.setStatus(model.getStatus());
        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserModalDetails::new)
                .orElseThrow( () -> new UsernameNotFoundException("Username not found: " + username ));
    }
}
