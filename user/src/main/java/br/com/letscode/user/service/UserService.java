package br.com.letscode.user.service;

import br.com.letscode.user.dto.UserRequest;
import br.com.letscode.user.model.Authority;
import br.com.letscode.user.model.User;
import br.com.letscode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRequest userRequest){
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEnabled(true);
        user.setRoles(
            userRequest.getRoles()
                    .stream()
                    .map(a -> Authority.convert(user, a))
                    .collect(Collectors.toList())
        );

        userRepository.save(user);
    }

}
