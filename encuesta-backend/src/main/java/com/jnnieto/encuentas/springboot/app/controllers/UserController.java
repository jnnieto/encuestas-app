package com.jnnieto.encuentas.springboot.app.controllers;

import com.jnnieto.encuentas.springboot.app.entities.UserEntity;
import com.jnnieto.encuentas.springboot.app.models.requests.UserRegisterRequestModel;
import com.jnnieto.encuentas.springboot.app.models.responses.UserRest;
import com.jnnieto.encuentas.springboot.app.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
