package com.jnnieto.encuestas.springboot.app.services;

import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.PollRest;

public interface PollService {

    public String createPoll(PollCreationRequestModel model, String email);

    public PollRest getPoll(String pollId);

}
