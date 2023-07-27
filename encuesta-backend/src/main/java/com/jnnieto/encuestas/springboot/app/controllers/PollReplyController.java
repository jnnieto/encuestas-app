package com.jnnieto.encuestas.springboot.app.controllers;

import com.jnnieto.encuestas.springboot.app.models.requests.PollReplyRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.CreatedPollReplyRest;
import com.jnnieto.encuestas.springboot.app.services.PollReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/polls/reply")
public class PollReplyController {

    @Autowired
    private PollReplyService pollReplyService;

    @PostMapping
    public ResponseEntity<CreatedPollReplyRest> replyPoll(@RequestBody @Valid PollReplyRequestModel model) {
        return ResponseEntity.status(HttpStatus.OK).body(new CreatedPollReplyRest(pollReplyService.createPollReply(model)));
    }

}
