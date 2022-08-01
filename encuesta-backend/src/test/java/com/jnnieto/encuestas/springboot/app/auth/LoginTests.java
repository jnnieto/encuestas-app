package com.jnnieto.encuestas.springboot.app.auth;

import com.jnnieto.encuestas.springboot.app.models.requests.UserLoginRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.UserRegisterRequestModel;
import com.jnnieto.encuestas.springboot.app.repositories.UserRepository;
import com.jnnieto.encuestas.springboot.app.services.UserService;
import com.jnnieto.encuestas.springboot.app.utils.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTests {

    private static final String API_LOGIN_URL = "/users/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void postLogin_withoutCredentials_returnForbidden() {
        ResponseEntity<Object> response = login(null, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void postLogin_withIncorrectCredentials_returnForbidden() {
        // Se crea un usuario aleatorio en base de datos
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        // Se crea un usuario que no existe para realizar el test
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail("abs@gmail.com");
        model.setPassword("123456");

        ResponseEntity<Object> response = login(model, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void postLogin_withCorrectCredentials_returnOk() {
        // Se crea un usuario aleatorio en base de datos
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        //Se envia el usuario que está en base de datos
        // para comparlo en el test
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Object> response = login(model, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postLogin_withCorrectCredentials_returnToken() {
        // Se crea un usuario aleatorio en base de datos
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        //Se envia el usuario que está en base de datos
        // para comparlo en el test
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());

        ResponseEntity<Map<String, String>> response = login(model, new ParameterizedTypeReference<Map<String, String>>(){});
        // Se toma el token del HashMap
        Map<String, String> body = response.getBody();
        String token = body.get("token");

        Assertions.assertTrue(token.contains("Bearer"));
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_LOGIN_URL, data, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, ParameterizedTypeReference<T> responseType) {
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }

}
