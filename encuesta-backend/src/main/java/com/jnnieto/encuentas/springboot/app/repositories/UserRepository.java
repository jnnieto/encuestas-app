package com.jnnieto.encuentas.springboot.app.repositories;

import com.jnnieto.encuentas.springboot.app.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
