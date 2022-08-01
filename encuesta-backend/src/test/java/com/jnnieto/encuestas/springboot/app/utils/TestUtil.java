package com.jnnieto.encuestas.springboot.app.utils;

import com.jnnieto.encuestas.springboot.app.models.requests.UserRegisterRequestModel;

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
