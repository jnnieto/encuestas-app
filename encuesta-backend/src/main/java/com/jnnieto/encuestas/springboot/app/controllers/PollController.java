package com.jnnieto.encuestas.springboot.app.controllers;


import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.CreatedPollRest;
import com.jnnieto.encuestas.springboot.app.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    PollService pollService;

    @PostMapping
    public CreatedPollRest createPoll(@RequestBody @Valid PollCreationRequestModel pollCreationRequestModel, Authentication authentication) {
        String pollId =  pollService.createPoll(pollCreationRequestModel, authentication.getPrincipal().toString());
        return new CreatedPollRest(pollId);
    }

}
