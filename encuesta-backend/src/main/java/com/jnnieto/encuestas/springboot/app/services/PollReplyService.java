package com.jnnieto.encuestas.springboot.app.services;

import com.jnnieto.encuestas.springboot.app.models.requests.PollReplyRequestModel;

public interface PollReplyService {

    public Long createPollReply(PollReplyRequestModel model);

}
