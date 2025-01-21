package edu.bbte.idde.dhim2228.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            @NonNull Object handler,
            Exception ex) {
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        int status = response.getStatus();
        log.info("Method: {}, URL: {}, Status: {}", method, url, status);
    }
}
