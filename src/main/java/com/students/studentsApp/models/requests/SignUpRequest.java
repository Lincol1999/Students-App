package com.students.studentsApp.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    private String firstname;

    private String lastname;

    private Set<String> roles;
}
