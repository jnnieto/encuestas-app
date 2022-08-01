package com.jnnieto.encuestas.springboot.app.annotations;


import com.jnnieto.encuestas.springboot.app.validators.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esto es una validación personalizada que valida
 * si el campo Email no esté repetido
 * la momento de la creación de un usuario
 */
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    /**
     * Mensaje por defecto cuando la validación no se cumple
     * @return
     */
    String message() default "{encuestas.constraints.email.unique.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
