package br.com.letscode.user.filter;

import br.com.letscode.user.model.User;
import br.com.letscode.user.repository.UserRepository;
import br.com.letscode.user.service.MongoAuthUserDetailService;
import br.com.letscode.user.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
//    private final MongoAuthUserDetailService mongoAuthUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        boolean isValid = tokenService.isTokenValid(token);

        if (isValid) {
            this.authenticate(token, request);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String token, HttpServletRequest request) {
        String usuario = tokenService.getTokenUser(token);
        Optional<User> userOptional = userRepository.findById(usuario);
        if (userOptional.isPresent()) {
//            UserDetails currentUserDetails = mongoAuthUserDetailService.loadUserByUsername(usuario);

            UsernamePasswordAuthenticationToken userToken =
                    new UsernamePasswordAuthenticationToken(userOptional.get(), null, userOptional.get().getAuthorities());
//            userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userToken);
        }
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7);
    }

}