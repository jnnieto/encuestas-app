package com.jnnieto.encuestas.springboot.app.services.impl;

import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.entities.PollReplyDetailEntity;
import com.jnnieto.encuestas.springboot.app.entities.PollReplyEntity;
import com.jnnieto.encuestas.springboot.app.exceptions.ConflictException;
import com.jnnieto.encuestas.springboot.app.models.requests.PollReplyRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.CreatedPollReplyRest;
import com.jnnieto.encuestas.springboot.app.repositories.PollReplyRepository;
import com.jnnieto.encuestas.springboot.app.repositories.PollRepository;
import com.jnnieto.encuestas.springboot.app.services.PollReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class PollReplyServiceImpl implements PollReplyService {

    private final PollReplyRepository pollReplyRepository;
    private final PollRepository pollRepository;

    public PollReplyServiceImpl(PollReplyRepository pollReplyRepository, PollRepository pollRepository) {
        this.pollReplyRepository = pollReplyRepository;
        this.pollRepository = pollRepository;
    }

    @Override
    public Long createPollReply(PollReplyRequestModel model) {
        PollEntity poll = this.pollRepository.findById(model.getPoll()).orElseThrow(() -> new ResolutionException("Poll not found"));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        PollReplyEntity pollReply = modelMapper.map(model, PollReplyEntity.class);
        pollReply.setPoll(poll);

        Set<Long> uniqueReplies = new HashSet<>();

        for (PollReplyDetailEntity pollReplyDetail: pollReply.getPollReplies()) {
            pollReplyDetail.setPollReply(pollReply);
            uniqueReplies.add(pollReplyDetail.getQuestionId());
        }

        if (uniqueReplies.size() != poll.getQuestions().size()) {
            throw new ConflictException("You must anwser all questions");
        }

        PollReplyEntity pollReplySaved = this.pollReplyRepository.save(pollReply);
        return pollReplySaved.getId();
    }

}
