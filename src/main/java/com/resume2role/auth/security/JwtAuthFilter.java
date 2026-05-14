package com.resume2role.auth.security;

import com.resume2role.auth.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        String path = req.getRequestURI();

        if(path.startsWith("/api/auth")){
            chain.doFilter(request,response);
            return;
        }

        String authHeader =
                req.getHeader("Authorization");

        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            ((HttpServletResponse)response)
                    .sendError(401,"Missing JWT");

            return;
        }

        String token =
                authHeader.substring(7);

        if(!jwtUtil.validateToken(token)) {

            ((HttpServletResponse)response)
                    .sendError(401,"Invalid JWT");

            return;
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        "user",
                        null,
                        java.util.Collections.emptyList()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        chain.doFilter(request,response);
    }
}