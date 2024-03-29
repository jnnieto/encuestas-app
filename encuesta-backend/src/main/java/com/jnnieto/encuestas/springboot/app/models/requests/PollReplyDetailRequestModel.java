package com.jnnieto.encuestas.springboot.app.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PollReplyDetailRequestModel {

    @NotNull
    @Positive
    private Long questionId;

    @NotNull
    @Positive
    private Long answerId;

}
