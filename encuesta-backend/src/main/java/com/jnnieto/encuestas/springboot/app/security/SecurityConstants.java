package com.jnnieto.encuestas.springboot.app.security;

import com.jnnieto.encuestas.springboot.app.SpringApplicationContext;

/**
 * Aqui se contienen todas las constante que se usan que el paquete de Security
 */
public class SecurityConstants {

    public static final long EXPIRATION_DATE = 864000000;  //Expiración de 10 días

    public static final String LOGIN_URL = "/users/login";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }

}
