package com.jnnieto.encuentas.springboot.app.entities;

import lombok.Data;

import javax.persistence.*;

@Entity()
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255, name = "last_name")
    private String lastName;

    @Column(nullable = false, length = 255, unique = true, name = "user_name")
    private String userName;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

}
