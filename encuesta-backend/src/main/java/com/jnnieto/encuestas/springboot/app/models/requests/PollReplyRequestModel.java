package com.jnnieto.encuestas.springboot.app.models.requests;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PollReplyRequestModel {

    @NotEmpty
    private String userName;

    @NotNull
    @Positive
    private Long poll;

    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<PollReplyDetailRequestModel> pollReplies;

}
