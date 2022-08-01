package com.jnnieto.encuestas.springboot.app.validators;

import com.jnnieto.encuestas.springboot.app.annotations.UniqueEmail;
import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * En esta clase de valida de que el correo nuevo no existe en base de datos
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserRepository userRepository;

    /**
     * Se verifica si existe algún email en base de datos
     * @param value el email de se quiere comparar
     * @param constraintValidatorContext
     * @return si existe se retorna true y si no false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        UserEntity user = userRepository.findByEmail(value);
        return user == null;
    }


}
