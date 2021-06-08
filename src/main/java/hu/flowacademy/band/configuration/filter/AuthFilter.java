package hu.flowacademy.band.configuration.filter;

import hu.flowacademy.band.services.auth.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtComponent;

    public AuthFilter(JwtProvider jwtComponent) {
        this.jwtComponent = jwtComponent;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String header = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            Authentication auth = jwtComponent.resolver(header);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}
