package com.jnnieto.encuestas.springboot.app.auth;

import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.models.requests.UserLoginRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.UserRegisterRequestModel;
import com.jnnieto.encuestas.springboot.app.models.responses.UserRest;
import com.jnnieto.encuestas.springboot.app.models.responses.ValidationErrors;
import com.jnnieto.encuestas.springboot.app.repositories.UserRepository;
import com.jnnieto.encuestas.springboot.app.services.UserService;
import com.jnnieto.encuestas.springboot.app.utils.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTests {

    private static final String API_URL = "/users";

    private static final String API_LOGIN_URL = "/users/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void createUser_withoutAnyFields_returnBadRequest() {
        ResponseEntity<Object> response = register(new UserRegisterRequestModel(), Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutNameField_returnBadRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        ResponseEntity<Object> response = register(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutPasswordField_returnBadRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<Object> response = register(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutEmailField_returnBadRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setEmail(null);
        ResponseEntity<Object> response = register(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutNamesFields_returnBadRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        user.setLastName(null);
        ResponseEntity<Object> response = register(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutUsernameField_returnBadRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setUserName(null);
        ResponseEntity<Object> response = register(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutAnyFields_returnValidationErrors() {
        ResponseEntity<ValidationErrors> response = register(new UserRegisterRequestModel(), ValidationErrors.class);
        // Se mapean los errores del HashMap
        Map<String, String> errors = response.getBody().getErrors();
        Assertions.assertEquals(errors.size(), 5);
    }

    @Test
    public void createUser_withoutNameField_returnValidationErrorMessageForName() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        ResponseEntity<ValidationErrors> response = register(user, ValidationErrors.class);
        // Se mapean los errores del HashMap
        Map<String, String> errors = response.getBody().getErrors();
        Assertions.assertTrue(errors.containsKey("name"));
    }

    @Test
    public void createUser_withoutEmailField_returnValidationErrorMessageForEmail() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setEmail(null);
        ResponseEntity<ValidationErrors> response = register(user, ValidationErrors.class);
        // Se mapean los errores del HashMap
        Map<String, String> errors = response.getBody().getErrors();
        Assertions.assertTrue(errors.containsKey("email"));
    }

    @Test
    public void createUser_withoutPasswordField_returnValidationErrorMessageForPassword() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<ValidationErrors> response = register(user, ValidationErrors.class);
        // Se mapean los errores del HashMap
        Map<String, String> errors = response.getBody().getErrors();
        Assertions.assertTrue(errors.containsKey("password"));
    }

    @Test
    public void createUser_withoutUserNameField_returnValidationErrorMessageForUserName() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setUserName(null);
        ResponseEntity<ValidationErrors> response = register(user, ValidationErrors.class);
        // Se mapean los errores del HashMap
        Map<String, String> errors = response.getBody().getErrors();
        Assertions.assertTrue(errors.containsKey("userName"));
    }

    @Test
    public void createUser_withValidUser_returnOk() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createUser_withValidUser_returnUserRest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        Assertions.assertEquals(response.getBody().getName(), user.getName());
    }

    @Test
    public void createUser_withValidUser_saveUserOnDB() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        Optional<UserEntity> userDB = userRepository.findById(response.getBody().getId());
        Assertions.assertNotNull(userDB);
    }

    @Test
    public void createUser_withValidUser_savePasswordWithHashOnDb() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        Optional<UserEntity> userDB = userRepository.findById(response.getBody().getId());
        Assertions.assertNotEquals(user.getPassword(), userDB.get().getPassword());
    }

    @Test
    public void createUser_withValidUserAndEmailAlreadyExist_returnBadRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        register(user, UserRest.class);
        ResponseEntity<UserRest> response2 = register(user, UserRest.class);
        Assertions.assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withValidUserAndEmailAlreadyExist_returnValidationErrorMessageForEmail() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        register(user, UserRest.class);
        ResponseEntity<ValidationErrors> response2 = register(user, ValidationErrors.class);
        // Se mapean los errores del HashMap
        Map<String, String> errors = response2.getBody().getErrors();
        Assertions.assertTrue(errors.containsKey("email"));
    }

    @Test
    public void getUser_WithoutToken_returnForbidden() {
        ResponseEntity<Object> response = getUser(null, new ParameterizedTypeReference<Object>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void getUser_WithToken_returnOk() {
        // Se crea un usuario aleatorio en base de datos
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        //Se envia el usuario que está en base de datos
        // para comparlo en el test
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String, String>> responseLogin = login(model, new ParameterizedTypeReference<Map<String, String>>(){});

        Map<String, String> body = responseLogin.getBody();
        String token = body.get("token").replace("Bearer ", "");
        ResponseEntity<UserRest> response = getUser(token, new ParameterizedTypeReference<UserRest>(){});
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getUser_WithToken_returnUserRest() {
        // Se crea un usuario aleatorio en base de datos
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        //Se envia el usuario que está en base de datos
        // para comparlo en el test
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String, String>> responseLogin = login(model, new ParameterizedTypeReference<Map<String, String>>(){});

        Map<String, String> body = responseLogin.getBody();
        String token = body.get("token").replace("Bearer ", "");
        ResponseEntity<UserRest> response = getUser(token, new ParameterizedTypeReference<UserRest>(){});
        Assertions.assertEquals(user.getName(), response.getBody().getName());
    }

    public <T> ResponseEntity<T> register(UserRegisterRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_URL, data, responseType);
    }

    public <T> ResponseEntity<T> getUser(String token, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);
        return testRestTemplate.exchange(API_URL, HttpMethod.GET, entity, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, ParameterizedTypeReference<T> responseType) {
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }

}
