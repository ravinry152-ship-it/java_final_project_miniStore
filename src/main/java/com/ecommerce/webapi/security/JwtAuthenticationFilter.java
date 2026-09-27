package com.ecommerce.webapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();

    @Override
    protected boolean shouldNotFilter(
            @Nonnull HttpServletRequest request
    ) {

        String path = request.getServletPath();

        return path.equals("/api/v1/auth/signup")
                || path.equals("/api/v1/auth/login");
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        System.out.println("=================================");
        System.out.println("JWT FILTER");
        System.out.println("Request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("Authorization exists: " + (authHeader != null));

        // No Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("No Bearer token");

            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7).trim();

        if (jwt.isEmpty()) {

            System.out.println("JWT is empty");

            filterChain.doFilter(request, response);
            return;
        }

        try {

            // =========================
            // Extract username/email
            // =========================

            String userEmail = jwtService.extractUsername(jwt);

            System.out.println("JWT username/email: " + userEmail);

            // =========================
            // Check SecurityContext
            // =========================

            if (userEmail != null &&
                    securityContextHolderStrategy
                            .getContext()
                            .getAuthentication() == null) {

                // =========================
                // Load User
                // =========================

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(userEmail);

                System.out.println(
                        "User found: " + userDetails.getUsername()
                );

                // =========================
                // Validate JWT
                // =========================

                boolean valid =
                        jwtService.isTokenValid(jwt, userDetails);

                System.out.println(
                        "JWT valid: " + valid
                );

                if (valid) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContext context =
                            securityContextHolderStrategy
                                    .createEmptyContext();

                    context.setAuthentication(authToken);

                    securityContextHolderStrategy.setContext(context);

                    System.out.println(
                            "Authentication SUCCESS"
                    );
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {

            System.out.println("JWT ERROR: EXPIRED");
            e.printStackTrace();

            sendUnauthorizedResponse(
                    response,
                    "JWT token has expired"
            );

        } catch (MalformedJwtException e) {

            System.out.println("JWT ERROR: MALFORMED");
            e.printStackTrace();

            sendUnauthorizedResponse(
                    response,
                    "Invalid JWT token"
            );

        } catch (SignatureException e) {

            System.out.println("JWT ERROR: INVALID SIGNATURE");
            e.printStackTrace();

            sendUnauthorizedResponse(
                    response,
                    "Invalid JWT signature"
            );

        } catch (Exception e) {

            System.out.println("JWT ERROR: OTHER");
            e.printStackTrace();

            sendUnauthorizedResponse(
                    response,
                    "Authentication failed: "
                            + e.getClass().getSimpleName()
            );
        }
    }

    private void sendUnauthorizedResponse(
            HttpServletResponse response,
            String message
    ) throws IOException {

        if (response.isCommitted()) {
            return;
        }

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                """
                {
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "%s"
                }
                """.formatted(message)
        );
    }
}