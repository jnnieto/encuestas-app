package com.jnnieto.encuestas.springboot.app.repositories;

import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    public UserEntity findByEmail(String email);

}
