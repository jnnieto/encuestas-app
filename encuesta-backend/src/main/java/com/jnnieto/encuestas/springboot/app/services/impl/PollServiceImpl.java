package com.jnnieto.encuestas.springboot.app.services.impl;

import com.jnnieto.encuestas.springboot.app.entities.AnswerEntity;
import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.entities.QuestionEntity;
import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.exceptions.ConflictException;
import com.jnnieto.encuestas.springboot.app.exceptions.ResourceNotFoundException;
import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.PollRest;
import com.jnnieto.encuestas.springboot.app.repositories.PollRepository;
import com.jnnieto.encuestas.springboot.app.repositories.UserRepository;
import com.jnnieto.encuestas.springboot.app.services.PollService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollServiceImpl implements PollService {

    PollRepository pollRepository;

    UserRepository userRepository;

    public PollServiceImpl(PollRepository pollRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createPoll(PollCreationRequestModel model, String email) {
        UserEntity user = userRepository.findByEmail(email);
        ModelMapper mapper = new ModelMapper();

        PollEntity pollEntity = mapper.map(model, PollEntity.class);
        pollEntity.setUser(user);
        pollEntity.setPollId(UUID.randomUUID().toString());

        for (QuestionEntity question : pollEntity.getQuestions()) {
            question.setPoll(pollEntity);
            for (AnswerEntity answer : question.getAnswers()) {
                answer.setQuestion(question);
            }
        }

        this.pollRepository.save(pollEntity);

        return pollEntity.getPollId();
    }

    @Override
    public PollRest getPoll(String pollId) {
        PollEntity poll = this.pollRepository.findByPollId(pollId);

        if (poll == null)
            throw new ResourceNotFoundException("Poll not found");

        if (!poll.isOpened())
            throw new ConflictException("Poll doesn't accept more replies");

        ModelMapper mapper = new ModelMapper();

        return mapper.map(poll, PollRest.class);
    }

}
