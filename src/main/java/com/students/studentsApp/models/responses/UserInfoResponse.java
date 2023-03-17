package com.students.studentsApp.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private int userId;

    private String username;

    private String email;

    private List<String> roles;

    private String token;

    private Date expiresAt;
}
