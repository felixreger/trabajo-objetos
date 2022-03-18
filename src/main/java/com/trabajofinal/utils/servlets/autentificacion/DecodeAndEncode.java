package com.trabajofinal.utils.servlets.autentificacion;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DecodeAndEncode {

    public static String encode(String value){
        Base64.Encoder e = Base64.getEncoder();
        return e.encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String value){
        byte[] b = Base64.getDecoder().decode(value);

        return new String(b, StandardCharsets.UTF_8);
    }

    public static String[] valuesAutentificacion(String authorization){
        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = DecodeAndEncode.decode(base64Credentials);
        return credentials.split(":", 2);
    }
}
