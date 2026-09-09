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
    protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth/");
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // =========================================
        // 1. No Authorization header
        // =========================================
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // =========================================
        // 2. Get JWT
        // =========================================
        final String jwt = authHeader.substring(7).trim();

        // Empty token
        if (jwt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            // =========================================
            // 3. Extract username
            // =========================================
            final String userEmail = jwtService.extractUsername(jwt);

            // =========================================
            // 4. Check SecurityContext
            // =========================================
            if (userEmail != null &&
                    securityContextHolderStrategy
                            .getContext()
                            .getAuthentication() == null) {

                // =========================================
                // 5. Load user
                // =========================================
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(userEmail);

                // =========================================
                // 6. Validate JWT
                // =========================================
                if (jwtService.isTokenValid(jwt, userDetails)) {

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
                            securityContextHolderStrategy.createEmptyContext();

                    context.setAuthentication(authToken);

                    securityContextHolderStrategy.setContext(context);
                }
            }

            // Continue request
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {

            // =========================================
            // JWT EXPIRED
            // =========================================
            sendUnauthorizedResponse(
                    response,
                    "JWT token has expired"
            );

        } catch (MalformedJwtException e) {

            // =========================================
            // JWT MALFORMED
            // =========================================
            sendUnauthorizedResponse(
                    response,
                    "Invalid JWT token"
            );

        } catch (SignatureException e) {

            // =========================================
            // INVALID SIGNATURE
            // =========================================
            sendUnauthorizedResponse(
                    response,
                    "Invalid JWT signature"
            );

        } catch (Exception e) {

            // =========================================
            // OTHER JWT / AUTH ERROR
            // =========================================
            sendUnauthorizedResponse(
                    response,
                    "Authentication failed"
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

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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