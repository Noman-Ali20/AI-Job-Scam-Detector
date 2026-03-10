package com.jobscanner.AIproject.config;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jobscanner.AIproject.entity.User;
import com.jobscanner.AIproject.repository.UserRepository;

import java.io.IOException;



public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public JwtFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	
    	if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
    	    response.setStatus(HttpServletResponse.SC_OK);
    	    filterChain.doFilter(request, response);
    	    return;
    	}
    	

        String path = request.getServletPath();

        if(path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or Invalid Token");
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = JwtUtil.extractEmail(token);

            User user = userRepository.findByEmailIgnoreCase(email);

            if(user == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid User");
                return;
            }

            request.setAttribute("role", user.getRole());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}