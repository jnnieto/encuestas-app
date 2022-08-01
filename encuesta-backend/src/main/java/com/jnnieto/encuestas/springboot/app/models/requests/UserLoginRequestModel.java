package com.jnnieto.encuestas.springboot.app.models.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginRequestModel extends UserRegisterRequestModel {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

}
