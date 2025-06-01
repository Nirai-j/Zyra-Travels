package com.zyra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> excludedPaths = Arrays.asList(
        "/auth/**",
        "/public/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"               
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String originalPath = request.getServletPath();
        log.debug("Original request path: {}", originalPath);
        
        // Remove /api prefix if it exists
        String normalizedPath = originalPath.startsWith("/api") 
            ? originalPath.substring(4) 
            : originalPath;
        
        log.debug("Normalized path for matching: {}", normalizedPath);
        
        boolean shouldNotFilter = excludedPaths.stream()
            .anyMatch(pattern -> pathMatcher.match(pattern, normalizedPath));
        log.debug("Path: {}, Should not filter: {}", normalizedPath, shouldNotFilter);
        return shouldNotFilter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();
            log.debug("Processing request: {} {}", request.getMethod(), path);
            
            String jwt = getJwtFromRequest(request);
            log.debug("JWT token present: {}", StringUtils.hasText(jwt));

            if (StringUtils.hasText(jwt)) {
                log.debug("Validating JWT token");
                if (tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUsernameFromJWT(jwt);
                    log.debug("JWT token valid for user: {}", username);
                    
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Set authentication for user: {}", username);
                } else {
                    log.debug("Invalid JWT token");
                }
            } else {
                log.debug("No JWT token found in request");
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.debug("Found Bearer token in request");
            return bearerToken.substring(7);
        }
        return null;
    }
} 