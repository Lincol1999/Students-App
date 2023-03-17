package com.students.studentsApp.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private String username;

    private String password;

    private String email;

    private String firstname;

    private String lastname;

    private Boolean status;
}
