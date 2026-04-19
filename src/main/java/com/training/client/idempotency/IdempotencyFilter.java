package com.training.client.idempotency;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IdempotencyFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(IdempotencyFilter.class);
    
    private final IdempotencyService idempotencyService;
    private final IdempotencyKeyGenerator keyGenerator;

    public IdempotencyFilter(IdempotencyService idempotencyService, IdempotencyKeyGenerator keyGenerator) {
        this.idempotencyService = idempotencyService;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String idempotencyKey = httpRequest.getHeader("Idempotency-Key");
        if (idempotencyKey == null) {
            String userId = httpRequest.getHeader("X-User-ID");
            String action = httpRequest.getRequestURI();
            idempotencyKey = keyGenerator.generateKeyForUserAction(
                userId != null ? Long.parseLong(userId) : 0L, action);
        }
        
        if (idempotencyService.isIdempotent(idempotencyKey)) {
            logger.info("Duplicate request detected: {}", idempotencyKey);
            httpResponse.setStatus(HttpServletResponse.SC_CONFLICT);
            httpResponse.getWriter().write("{\"error\":\"Duplicate request\"}");
            return;
        }
        
        chain.doFilter(request, response);
    }
}