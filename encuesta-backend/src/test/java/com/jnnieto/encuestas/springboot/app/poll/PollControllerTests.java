package com.jnnieto.encuestas.springboot.app.poll;

import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.UserLoginRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.UserRegisterRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.PollRest;
import com.jnnieto.encuestas.springboot.app.models.responses.ValidationErrors;
import com.jnnieto.encuestas.springboot.app.repositories.PollRepository;
import com.jnnieto.encuestas.springboot.app.repositories.UserRepository;
import com.jnnieto.encuestas.springboot.app.services.PollService;
import com.jnnieto.encuestas.springboot.app.services.UserService;
import com.jnnieto.encuestas.springboot.app.utils.TestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PollControllerTests {

    private static final String API_URL = "/polls";

    private static final String API_LOGIN_URL = "/users/login";

    private String token = "";

    private UserEntity user = null;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    PollService pollService;

    @BeforeAll
    public void initializeObjects() {
        // Se crea un usuario aleatorio en base de datos
        UserRegisterRequestModel user = TestUtil.createValidUser();
        this.user = userService.createUser(user);
        //Se envia el usuario que está en base de datos
        // para comparlo en el test
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String, String>> response = login(model, new ParameterizedTypeReference<Map<String, String>>(){});
        // Se toma el token del HashMap
        Map<String, String> body = response.getBody();
        this.token = body.get("token");
    }

    @AfterEach
    public void cleanup() {
        pollRepository.deleteAll();
    }

    @AfterAll
    public void cleanupAfter() {
        userRepository.deleteAll();;
    }

    @Test
    public void createPoll_WithoutAuth_ReturnForbidden() {
        ResponseEntity<Object> response = createPoll(new PollCreationRequestModel(), Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void createPoll_WithAuthWithoutData_ReturnBadRequest() {
        ResponseEntity<ValidationErrors> response = createPoll(new PollCreationRequestModel(), new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithoutContentPoll_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithoutContentPoll_ReturnValidationErrorToContent() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertTrue(response.getBody().getErrors().containsKey("content"));
    }

    @Test
    public void createPoll_WithAuthWithoutQuestionsToPoll_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.setQuestions(null);
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithoutQuestionsToPoll_ReturnValidationErrorToAnswers() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.setQuestions(null);
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>() {
        });
        Assertions.assertTrue(response.getBody().getErrors().containsKey("questions"));
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithoutContent_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithoutContent_ReturnValidationErrorToContentAnswer() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertTrue(response.getBody().getErrors().containsKey("questions[0].content"));
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithBadOrder_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setQuestionOrder(0);
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithBadOrder_ReturnValidationErrorToQuestionOrder() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setQuestionOrder(0);
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertTrue(response.getBody().getErrors().containsKey("questions[0].questionOrder"));
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithInvalidType_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setType("HELLO");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithInvalidType_ReturnValidationErrorToQuestionType() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setType("HELLO");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertTrue(response.getBody().getErrors().containsKey("questions[0].type"));
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithoutAnswers_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setAnswers(null);
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithoutAnswers_ReturnValidationErrorToQuestionsList() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).setAnswers(null);
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertTrue(response.getBody().getErrors().containsKey("questions[0].answers"));
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithoutAnswersContent_ReturnBadRequest() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).getAnswers().get(0).setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createPoll_WithAuthWithValidQuestionsWithoutAnswersContent_ReturnValidationErrorToContentAnswer() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        poll.getQuestions().get(0).getAnswers().get(0).setContent("");
        ResponseEntity<ValidationErrors> response = createPoll(poll, new ParameterizedTypeReference<ValidationErrors>(){});
        Assertions.assertTrue(response.getBody().getErrors().containsKey("questions[0].answers[0].content"));
    }

    @Test
    public void createPoll_WithAuthWithValidPoll_ReturnOk() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        ResponseEntity<Object> response = createPoll(poll, new ParameterizedTypeReference<Object>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createPoll_WithAuthWithValidPoll_ReturnCreatedPollId() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        ResponseEntity<Map<String, String>> response = createPoll(poll, new ParameterizedTypeReference<Map<String, String>>(){});
        Map<String, String> body = response.getBody();
        Assertions.assertTrue(body.containsKey("pollId"));
    }

    @Test
    public void createPoll_WithAuthWithValidPoll_SaveToDatabase() {
        PollCreationRequestModel poll = TestUtil.createValidPoll();
        ResponseEntity<Map<String, String>> response = createPoll(poll, new ParameterizedTypeReference<Map<String, String>>(){});
        Map<String, String> body = response.getBody();
        PollEntity pollDB = pollRepository.findByPollId(body.get("pollId"));
        Assertions.assertNotNull(pollDB);
    }

    @Test
    public void getPollWithQuestions_whenThePollDoNotExistsOnDatabase_ReturnNotFound() {
        ResponseEntity<Object> response = getPollWithQuestions(API_URL + "/uuid/questions", Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getPollWithQuestions_whenThePollExistsOnDatabase_ReturnOk() {
        PollCreationRequestModel model = TestUtil.createValidPoll();
        String uuid = pollService.createPoll(model, user.getEmail());
        ResponseEntity<Object> response = getPollWithQuestions(API_URL + "/" + uuid + "/questions", Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getPollWithQuestions_whenThePollExistsOnDatabase_ReturnPollRest() {
        PollCreationRequestModel model = TestUtil.createValidPoll();
        String uuid = pollService.createPoll(model, user.getEmail());
        ResponseEntity<PollRest> response = getPollWithQuestions(API_URL + "/" + uuid + "/questions", PollRest.class);
        Assertions.assertEquals(uuid, response.getBody().getPollId());
    }

    public <T> ResponseEntity<T> getPollWithQuestions(String url, Class<T> responseType) {
        return testRestTemplate.getForEntity(url, responseType);
    }

    public <T> ResponseEntity<T> createPoll(PollCreationRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_URL, data, responseType);
    }

    public <T> ResponseEntity<T> createPoll(PollCreationRequestModel data, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<PollCreationRequestModel> entity = new HttpEntity<PollCreationRequestModel>(data, headers);
        return testRestTemplate.exchange(API_URL, HttpMethod.POST, entity, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, ParameterizedTypeReference<T> responseType) {
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }

}
