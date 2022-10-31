package com.jnnieto.encuestas.springboot.app.utils;

import com.jnnieto.encuestas.springboot.app.models.requests.AnswerCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.PollCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.QuestionCreationRequestModel;
import com.jnnieto.encuestas.springboot.app.models.requests.UserRegisterRequestModel;

import java.util.ArrayList;
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
