package com.example.trendtyschool.config;


import com.example.trendtyschool.service.AccountService;
import com.example.trendtyschool.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreFilter extends OncePerRequestFilter {

    private final AccountService accountService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");

        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            // Nếu không có token hoặc không bắt đầu bằng 'Bearer'
//            String errorMessage = "Missing or invalid Authorization header";
//            log.info("lỗi ồi");
//
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorization.substring("Bearer".length()).trim();
        String username = null;

        try {
            username = jwtService.extractUsername(token);
            log.info("Extracted username: {}", username);
        } catch (Exception e) {
            String errorMessage = "Invalid or expired token ";
            log.error(errorMessage);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");

            filterChain.doFilter(request, response);
            return;
        }


        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = accountService.loadUserByUsername(username);

            if (jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
                log.info("Authentication successful for user: {}", username);
            } else {
                String errorMessage = "Invalid or expired token ";

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
                filterChain.doFilter(request, response);

                return;
            }
        }

        filterChain.doFilter(request, response);
    }


}
