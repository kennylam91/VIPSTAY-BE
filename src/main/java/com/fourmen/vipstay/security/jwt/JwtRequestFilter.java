package com.fourmen.vipstay.security.jwt;


import com.fourmen.vipstay.security.service.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//The JwtRequestFilter extends the
// Spring Web Filter OncePerRequestFilter class.
// For any incoming request this Filter class gets executed.
// It checks if the request has a valid JWT token.
// If it has a valid JWT Token then it sets the Authentication
// in the context, to specify that the current user
// is authenticated.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsServiceImpl;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if (jwt != null && jwtTokenUtil.validateToken(jwt)) {
                String username = jwtTokenUtil.getUsernameFromToken(jwt);

                UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
