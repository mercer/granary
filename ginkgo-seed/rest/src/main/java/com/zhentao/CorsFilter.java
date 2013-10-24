package com.zhentao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    private FilterConfig filterConfig = null;
    private String allowOrigin;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (clientChecksIfPathIsAccessibleViaCors(request)) {
            replyThatCorsIsEnabled(response);
            return;
        }else if(regularCorsCall(request)){
            response.addHeader("Access-Control-Allow-Origin", allowOrigin);
            response.addHeader("Access-Control-Allow-Credentials","true");
        }
        filterChain.doFilter(request, response);

    }

    private void replyThatCorsIsEnabled(HttpServletResponse response) {
        // CORS "pre-flight" request
        response.addHeader("Access-Control-Allow-Origin", allowOrigin);
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1");// 30 min
        response.setStatus(200);
    }

    private boolean regularCorsCall(HttpServletRequest request) {
        return request.getHeader("Origin") != null;
    }

    private boolean clientChecksIfPathIsAccessibleViaCors(HttpServletRequest request) {
        return "OPTIONS".equals(request.getMethod()) && request.getHeader("Access-Control-Request-Method") != null;
    }

    protected void initFilterBean() throws ServletException {
        allowOrigin = getFilterConfig().getInitParameter("allow-origin");
        if(allowOrigin == null){
            allowOrigin = "*";
        }
    }
}