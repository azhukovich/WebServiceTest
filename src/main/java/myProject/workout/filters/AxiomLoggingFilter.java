package myProject.workout.filters;

import myProject.workout.service.AxiomClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class AxiomLoggingFilter extends OncePerRequestFilter {

    private final AxiomClient axiomClient;

    public AxiomLoggingFilter(AxiomClient axiomClient) {
        this.axiomClient = axiomClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        Map<String, Object> log = new HashMap<>();
        log.put("method", request.getMethod());
        log.put("path", request.getRequestURI());
        log.put("query", request.getQueryString());
        log.put("status", response.getStatus());
        log.put("duration_ms", duration);
        log.put("timestamp", Instant.now().toString());

        axiomClient.sendLog(log);
    }
}

