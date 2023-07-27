package com.jnnieto.encuestas.springboot.app.models.responses;

import com.jnnieto.encuestas.springboot.app.enums.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRest {

    private Long id;

    private String content;

    private Integer questionOrder;

    private QuestionType type;

    private List<AnswerRest> answers;

}
