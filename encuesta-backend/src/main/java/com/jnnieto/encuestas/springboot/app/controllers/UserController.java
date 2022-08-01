package com.jnnieto.encuestas.springboot.app.controllers;

import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.models.requests.UserRegisterRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.UserRest;
import com.jnnieto.encuestas.springboot.app.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserRest createUser(@RequestBody @Valid UserRegisterRequestModel userModel) {
        UserEntity user = userService.createUser(userModel);
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(user, userRest);
        return userRest;
    }

    @GetMapping
    public UserRest getUser(Authentication authentication) {
        String userEmail = authentication.getPrincipal().toString();
        UserRest userRest = new UserRest();
        UserEntity user = userService.getUser(userEmail);
        BeanUtils.copyProperties(user, userRest);
        return userRest;
    }
}
