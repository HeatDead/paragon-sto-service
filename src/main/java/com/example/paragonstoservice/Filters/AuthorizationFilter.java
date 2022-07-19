package com.example.paragonstoservice.Filters;

import com.example.paragonstoservice.Services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Value("${auth.enabled}")
    private boolean enabled;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> AUTH_WHITELIST = Arrays.asList("/swagger-ui/**", "/swagger-resources/**", "/v2/**");

    private static final List<String> ADMIN_WHITELIST = Arrays.asList("/works/addType", "/parts/addType", "/parts/addPart");
    private static final List<String> MODERATOR_WHITELIST = Arrays.asList("/works/addType", "/parts/addType", "/parts/addPart");
    private static final List<String> WORKER_WHITELIST = Arrays.asList("/works/addWork");
    private static final List<String> MSERVICE_WHITELIST = Arrays.asList("/parts/order");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        return AUTH_WHITELIST.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    protected boolean adminFilter(HttpServletRequest request)
            throws ServletException {
        return ADMIN_WHITELIST.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    protected boolean moderatorFilter(HttpServletRequest request)
            throws ServletException {
        return MODERATOR_WHITELIST.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    protected boolean workerFilter(HttpServletRequest request)
            throws ServletException {
        return WORKER_WHITELIST.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    protected boolean microserviceFilter(HttpServletRequest request)
            throws ServletException {
        return MSERVICE_WHITELIST.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!enabled)
            filterChain.doFilter(request, response);

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isBlank()) {
            System.out.println("SC_UNAUTHORIZED : " + authHeader + " " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else if (!checkAuthorization(authHeader)) {
            System.out.println("SC_FORBIDDEN");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else {
            String token = authHeader.substring(7);
            if(adminFilter(request)) {
                System.out.println("ADMIN REQUEST");
                if (tokenService.checkRole(token).equals("ADMIN"))
                    filterChain.doFilter(request, response);
            }
            if(moderatorFilter(request)) {
                System.out.println("MODERATOR REQUEST");
                if (tokenService.checkRole(token).equals("MODERATOR"))
                    filterChain.doFilter(request, response);
            }
            if (workerFilter(request)) {
                System.out.println("WORKER REQUEST");
                if (tokenService.checkRole(token).equals("WORKER"))
                    filterChain.doFilter(request, response);
            }
            if (microserviceFilter(request)) {
                System.out.println("MICROSERVICE REQUEST");
                if (tokenService.checkRole(token).equals("MICROSERVICE"))
                    filterChain.doFilter(request, response);
            }

            if (!microserviceFilter(request) && !workerFilter(request) && !moderatorFilter(request) && !adminFilter(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean checkAuthorization(String auth) {
        if (!auth.startsWith("Bearer ")){
            System.out.println(auth + " - " + "no 'Bearer'");
            return false;
        }

        String token = auth.substring(7);
        return tokenService.checkToken(token);
    }
}
