package com.vetlife.api.shared.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) {
        log.info("[AUDITORIA] Requisição Recebida: Método [{}] - URI: {}", request.getMethod(), request.getRequestURI());
        return true;
    }
}