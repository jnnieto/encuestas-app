package com.jnnieto.encuestas.springboot.app.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrors {

    /**
     * Campo que almacena los errores con el nombre el campo y el mensaje de error
     */
    private Map<String, String> errors;

    /**
     * Campo que almacena la fecha en que se produce el error
     */
    private Date timeStamp;

}
