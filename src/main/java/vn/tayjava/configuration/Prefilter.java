package vn.tayjava.configuration;

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
import vn.tayjava.service.JwtService;
import vn.tayjava.service.UserService;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Prefilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("------ PREFILTER ------");

        final String authorization = request.getHeader("Authorization");
        log.info("Authorization: {}", authorization);
        if (StringUtils.isNotBlank(authorization) && !authorization.startsWith("Bearer ") ) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authorization.substring(7);
        log.info("Token: {}", token);
        final String username = jwtService.extractUsername(token);
        log.info("Username from token: {}", username);
        if(StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if(jwtService.isTokenValid(token, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                log.info("Token is valid. User authenticated: {}", username);
            } else {
                log.info("Token is invalid");
            }
        }


        filterChain.doFilter(request, response);
    }
}
