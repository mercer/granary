package com.zhentao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ((request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) || (request.getHeader("Origin") != null)) {
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            response.addHeader("Access-Control-Allow-Credentials","true");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Max-Age", "1");// 30 min

            logger.info(".........................");

        }
        filterChain.doFilter(request, response);
    }
}