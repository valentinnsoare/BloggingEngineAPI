package io.valentinsoare.bloggingengineapi.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.valentinsoare.bloggingengineapi.caching.CachedBodyHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

        try {
            logRequest(cachedRequest);
            filterChain.doFilter(cachedRequest, response);
        } finally {
            logResponse(response);
        }
    }

    private void logRequest(CachedBodyHttpServletRequest cachedRequest) throws ServletException, IOException {
        String messageToBeSavedInLogs = String.format("\033[1;32mRequest -> request_id: %s, method: %s, uri: %s, user: %s body: %s\033[0m",
                cachedRequest.getRequestId(), cachedRequest.getMethod(), cachedRequest.getRequestURI(),
                cachedRequest.getRemoteUser(), cachedRequest.getCachedBody());
        log.info(messageToBeSavedInLogs);
    }

    private void logResponse(HttpServletResponse response) {
        String msgToPrint = String.format("\033[1;32mResponse -> status: %s, content-type: %s, character-encoding: %s, buffer-size: %s, locale: %s\033[0m",
                response.getStatus(), response.getContentType(), response.getCharacterEncoding(),
                response.getBufferSize(), response.getLocale());
        log.info(msgToPrint);
    }
}
