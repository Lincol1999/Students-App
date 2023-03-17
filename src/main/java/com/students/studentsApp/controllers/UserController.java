package com.students.studentsApp.controllers;

import com.students.studentsApp.configurations.Utils.JwtUtils;
import com.students.studentsApp.entities.Role;
import com.students.studentsApp.entities.User;
import com.students.studentsApp.models.constants.ERole;
import com.students.studentsApp.models.requests.LoginRequest;
import com.students.studentsApp.models.UserModalDetails;
import com.students.studentsApp.models.UserModel;
import com.students.studentsApp.models.requests.SignUpRequest;
import com.students.studentsApp.models.responses.MessageResponse;
import com.students.studentsApp.models.responses.UserInfoResponse;
import com.students.studentsApp.repositories.RoleRepository;
import com.students.studentsApp.repositories.UserRepository;
import com.students.studentsApp.services.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping(value = "")
    ResponseEntity<User> createUser(@RequestBody UserModel userModel) {
        try {
            User newUser = userService.register(userModel);
            log.info("User created");
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error while creating user");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserModalDetails userModalDetails = (UserModalDetails) authentication.getPrincipal();

        String token = jwtUtils.generateTokenFromUsername(userModalDetails.getUsername());
        Date expiresAt = jwtUtils.getExpirationDataFromToken(token);

        List<String> roles = userModalDetails
                .getAuthorities()
                .stream()
                .map((item) -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(new UserInfoResponse(
                        userModalDetails.getUserId(),
                        userModalDetails.getUsername(),
                        userModalDetails.getEmail(),
                        roles,
                        token,
                        expiresAt
                ));
    }

    @PostMapping(value = "signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody @NonNull SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email al already in use!"));
        }

        //Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setStatus(true);

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        //Si no tienes un rol asignado a tu usuario
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role is no found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;

                    case "supervisor":
                        Role modRole = roleRepository.findByRoleName(ERole.ROLE_SUPERVISOR.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity
                .ok(new MessageResponse("User registered successfully!!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

}
