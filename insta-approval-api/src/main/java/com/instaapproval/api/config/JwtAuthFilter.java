package com.instaapproval.api.config;

import com.instaapproval.api.entity.User;
import com.instaapproval.api.repo.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthFilter extends GenericFilter {
    private final JwtService jwtService;
    private final UserRepository userRepo;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtService.parse(token).getBody();
                String username = claims.getSubject();
                String role = (String) claims.get("role");
                Optional<User> userOpt = userRepo.findByUsername(username);
                if (userOpt.isPresent()) {
                    List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null, auths);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {}
        }
        chain.doFilter(request, response);
    }
}
