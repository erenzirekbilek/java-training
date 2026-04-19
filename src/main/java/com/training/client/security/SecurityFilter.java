package com.training.client.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    
    private final JwtTokenService tokenService;

    public SecurityFilter(JwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String token = httpRequest.getHeader("Authorization");
        if (token != null && tokenService.validateToken(token)) {
            logger.info("Authenticated request");
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"error\":\"Unauthorized\"}");
        }
    }
}