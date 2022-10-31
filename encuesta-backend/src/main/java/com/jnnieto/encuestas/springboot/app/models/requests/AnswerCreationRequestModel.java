package com.jnnieto.encuestas.springboot.app.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnswerCreationRequestModel {

    @NotEmpty
    private String content;

}
