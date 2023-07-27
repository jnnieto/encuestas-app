package com.jnnieto.encuestas.springboot.app.utils;

import com.jnnieto.encuestas.springboot.app.entities.AnswerEntity;
import com.jnnieto.encuestas.springboot.app.entities.PollEntity;
import com.jnnieto.encuestas.springboot.app.entities.QuestionEntity;
import com.jnnieto.encuestas.springboot.app.entities.UserEntity;
import com.jnnieto.encuestas.springboot.app.enums.QuestionType;
import com.jnnieto.encuestas.springboot.app.models.requests.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtil {

    /**
     * Método que crea un usuario aleatorio pasa usarse
     * como prueba en los tests
     * @return el usuario aleatorio de tipo UserRegisterRequestModel
     */
    public static UserRegisterRequestModel createValidUser() {
        UserRegisterRequestModel user = new UserRegisterRequestModel();
        user.setEmail(generateRandomString(16) + "@gmail.com");
        user.setUserName(generateRandomString(8));
        user.setName(generateRandomString(8));
        user.setLastName(generateRandomString(8));
        user.setPassword(generateRandomString(8));
        System.out.println(user.toString());
        return user;
    }

    /**
     * Método que crea una escuesta válida de forma aleatoria
     * @return la encuesta válida creada
     */
    public static PollCreationRequestModel createValidPoll() {
        ArrayList<AnswerCreationRequestModel> answers = new ArrayList<>();
        AnswerCreationRequestModel answer = new AnswerCreationRequestModel();
        answer.setContent(generateRandomString(16));
        answers.add(answer);

        ArrayList<QuestionCreationRequestModel> questions = new ArrayList<>();
        QuestionCreationRequestModel question = new QuestionCreationRequestModel();
        question.setContent(generateRandomString(16));
        question.setQuestionOrder(1);
        question.setType("CHECKBOX");
        question.setAnswers(answers);
        questions.add(question);

        PollCreationRequestModel poll = new PollCreationRequestModel();
        poll.setContent(generateRandomString(16));
        poll.setOpened(true);
        poll.setQuestions(questions);

        return poll;
    }

    public static PollEntity createValidPollEntity(UserEntity user) {

        PollEntity poll = new PollEntity();
        poll.setContent(generateRandomString(16));
        poll.setOpened(true);
        poll.setUser(user);
        poll.setPollId(UUID.randomUUID().toString());

        ArrayList<QuestionEntity> questions = new ArrayList<>();
        QuestionEntity question = new QuestionEntity();
        question.setContent(generateRandomString(16));
        question.setQuestionOrder(1);
        question.setType(QuestionType.CHECKBOX);
        question.setPoll(poll);
        questions.add(question);


        ArrayList<AnswerEntity> answers = new ArrayList<>();
        AnswerEntity answer = new AnswerEntity();
        answer.setContent(generateRandomString(16));
        answer.setQuestion(question);
        answers.add(answer);
        question.setAnswers(answers);
        poll.setQuestions(questions);

        return poll;
    }

    public static PollReplyRequestModel createValidPollReply(PollEntity poll) {
        ArrayList<PollReplyDetailRequestModel> pollReplies = new ArrayList<>();
        PollReplyDetailRequestModel reply = new PollReplyDetailRequestModel();

        reply.setQuestionId(poll.getQuestions().get(0).getId());
        reply.setAnswerId(poll.getQuestions().get(0).getAnswers().get(0).getId());
        pollReplies.add(reply);

        PollReplyRequestModel model = new PollReplyRequestModel();
        model.setUserName(generateRandomString(8));
        model.setPoll(poll.getId());
        model.setPollReplies(pollReplies);

        return model;
    }

    /**
     * Método que genera una cadena aleatoria de strings
     * @param len cantidad de caracteres
     * @return la cadena con letras aleatoria
     */
    private static String generateRandomString(int len) {
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < len; x++) {
            int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }

    private static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

}
