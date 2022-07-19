package com.jnnieto.encuentas.springboot.app.services;

import com.jnnieto.encuentas.springboot.app.entities.UserEntity;
import com.jnnieto.encuentas.springboot.app.models.requests.UserRegisterRequestModel;
import com.jnnieto.encuentas.springboot.app.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserRegisterRequestModel userModel) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);

        this.userRepository.save(userEntity);

        return userEntity;
    }

}
