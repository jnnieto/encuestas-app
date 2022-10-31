package com.jnnieto.encuestas.springboot.app.annotations;

import com.jnnieto.encuestas.springboot.app.validators.ValueOfEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValueOfEnumValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();

    String message() default "{encuestas.constraints.enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
