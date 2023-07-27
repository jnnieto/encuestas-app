package com.jnnieto.encuestas.springboot.app.controllers;


import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.CreatedPollRest;
import com.jnnieto.encuestas.springboot.app.models.responses.PollRest;
import com.jnnieto.encuestas.springboot.app.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}/questions")
    public ResponseEntity<PollRest> getPollWithQuestions(@PathVariable String id) {
        PollRest poll = pollService.getPoll(id);
        return ResponseEntity.status(HttpStatus.OK).body(poll);
    }

}
