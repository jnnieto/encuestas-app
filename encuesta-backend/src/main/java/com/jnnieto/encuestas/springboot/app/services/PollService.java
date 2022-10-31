package com.jnnieto.encuestas.springboot.app.services;

import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;

public interface PollService {

    public String createPoll(PollCreationRequestModel model, String email);

}
