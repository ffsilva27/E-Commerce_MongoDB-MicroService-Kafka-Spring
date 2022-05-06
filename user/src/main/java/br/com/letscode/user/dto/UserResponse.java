package br.com.letscode.user.dto;

import br.com.letscode.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UserResponse {
    private String username;
    private List<String> roles;

    public static UserResponse convert(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setRoles(user.getRoles()
                .stream()
                .map(authority -> authority.getAuthorityKey())
                .collect(Collectors.toList())
        );
        return userResponse;
    }
}
