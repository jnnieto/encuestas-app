package com.jnnieto.encuentas.springboot.app.services;

import com.jnnieto.encuentas.springboot.app.entities.UserEntity;
import com.jnnieto.encuentas.springboot.app.models.requests.UserRegisterRequestModel;

public interface UserService {

    public UserEntity createUser(UserRegisterRequestModel userModel);

}
