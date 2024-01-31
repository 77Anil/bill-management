package com.billnex.billManage.config;

import com.billnex.billManage.service.JWTService;
import com.billnex.billManage.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Profile("!test")
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;
    
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        String email = jwtService.extractUsername(jwtToken);

        if(StringUtils.hasLength(email) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);
            if(jwtService.isTokenValid(jwtToken, userDetails)){
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                emptyContext.setAuthentication(token);
                SecurityContextHolder.setContext(emptyContext);
            }
        }

        filterChain.doFilter(request, response);

    }
}
