package com.usermanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userid;
    private String name;

    private String email;
    private String token;

    public UserResponse(String userid, String name, String email) {
        this.userid = userid;
        this.name = name;
        this.email = email;

    }
}
