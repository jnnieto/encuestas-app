package com.jnnieto.encuentas.springboot.app.models.responses;

import lombok.Data;

@Data
public class UserRest {

    private Long id;

    private String name;

    private String lastName;

    private String userName;

    private String email;

}
