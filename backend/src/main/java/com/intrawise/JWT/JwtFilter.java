package com.intrawise.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String userId = jwtUtil.extractUserId(token);

                if (userId != null && jwtUtil.validateToken(userId, token)) {

                    String role = jwtUtil.extractRole(token).toUpperCase().trim();

                    String grantedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;

                    User principal = new User(userId, "",
                            Collections.singletonList(new SimpleGrantedAuthority(grantedRole)));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    principal.getAuthorities()
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            } catch (Exception ex) {
                System.err.println("Invalid JWT Token: " + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
    
//    @Component
//    public class DebugAuthFilter extends OncePerRequestFilter {
//        @Override
//        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//                throws ServletException, IOException {
//            var auth = SecurityContextHolder.getContext().getAuthentication();
//            System.out.println("🔍 In DebugAuthFilter, before controller:");
//            System.out.println("Authentication: " + auth);
//            if (auth != null) {
//                System.out.println("Authorities: " + auth.getAuthorities());
//            }
//            chain.doFilter(request, response);
//        }
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
    	String path = request.getServletPath();
    	return path.startsWith("/api/v1/auth/login") || path.startsWith("/api/v1/auth/register");
    }
}
