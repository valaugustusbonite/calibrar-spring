package com.calibrar.identityservice.common.constants;

public class SecurityConstants {
    public static final String SECRET_KEY = "9y$B&E)H@McQfTjWmZq4t7w!z%C*F-JaNdRgUkXp2r5u8x/A?D(G+KbPeShVmYq3";
    public static final String REGISTER_PATH = "/api/v1/user/register";
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String LOGIN_PATH = "/api/v1/user/login";
}
