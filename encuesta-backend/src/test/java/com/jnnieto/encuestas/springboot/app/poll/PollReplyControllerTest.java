package com.jnnieto.encuestas.springboot.app.poll;

import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.entities.PollReplyEntity;
import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.models.requests.PollReplyRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.ValidationErrors;
import com.jnnieto.encuestas.springboot.app.repositories.PollReplyRepository;
import com.jnnieto.encuestas.springboot.app.repositories.PollRepository;
import com.jnnieto.encuestas.springboot.app.repositories.UserRepository;
import com.jnnieto.encuestas.springboot.app.services.UserService;
import com.jnnieto.encuestas.springboot.app.utils.TestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PollReplyControllerTest {

    private static final String API_URL = "/polls/reply";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    PollReplyRepository pollReplyRepository;

    PollEntity poll;

    @BeforeAll
    public void initializedObjects() {
        UserEntity user = userService.createUser(TestUtil.createValidUser());
        poll = pollRepository.save(TestUtil.createValidPollEntity(user));
    }

    @AfterAll
    public void cleanupAfter() {
        pollRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        pollReplyRepository.deleteAll();
    }

    @Test
    public void replyPoll_WithoutUser_ReturnBadRequest() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.setUserName(null);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void replyPoll_WithoutUser_ReturnValidationError() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.setUserName(null);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertTrue(response.getBody().getErrors().containsKey("userName"));
    }


    @Test
    public void replyPoll_WithInvalidPollId_ReturnBadRequest() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.setPoll(0L);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void replyPoll_WithInvalidPollId_ReturnValidationError() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.setUserName(null);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertTrue(response.getBody().getErrors().containsKey("poll"));
    }

    @Test
    public void replyPoll_WithEmptyPollReplies_ReturnBadRequest() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.setPollReplies(null);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void replyPoll_WithEmptyPollReplies_ReturnValidationError() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.setPollReplies(null);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertTrue(response.getBody().getErrors().containsKey("pollReplies"));
    }

    @Test
    public void replyPoll_WithInvalidQuestionId_ReturnBadRequest() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.getPollReplies().get(0).setQuestionId(0L);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void replyPoll_WithInvalidQuestionId_ReturnValidationError() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.getPollReplies().get(0).setQuestionId(0L);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertTrue(response.getBody().getErrors().containsKey("pollReplies[0].questionId"));
    }

    @Test
    public void replyPoll_WithInvalidAnswerId_ReturnBadRequest() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.getPollReplies().get(0).setAnswerId(0L);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void replyPoll_WithInvalidAnswerId_ReturnValidationError() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        model.getPollReplies().get(0).setAnswerId(0L);
        ResponseEntity<ValidationErrors> response = createPollReply(model, ValidationErrors.class);
        assertTrue(response.getBody().getErrors().containsKey("pollReplies[0].answerId"));
    }

    @Test
    public void replyPoll_WithDataValid_ReturnOk() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        ResponseEntity<Object> response = createPollReply(model, Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void replyPoll_WithDataValid_saveToDatabase() {
        PollReplyRequestModel model = TestUtil.createValidPollReply(poll);
        createPollReply(model, PollReplyRequestModel.class);
        List<PollReplyEntity> replies = (List<PollReplyEntity>) pollReplyRepository.findAll();
        assertEquals(1, replies.size());
    }

    public <T> ResponseEntity<T> createPollReply(PollReplyRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_URL, data, responseType);
    }

}