package com.cegeka.ginkgo.application.security;


import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        if (exception instanceof DisabledException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email not yet confirmed");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad credentials");
        }
    }
}
