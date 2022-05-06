package br.com.letscode.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    private String authorityKey;

    public static Authority convert(User user, String role) {
        Authority authority = new Authority();

        authority.setAuthorityKey(role);

        return authority;
    }

    @Override
    public String getAuthority() {
        return this.getAuthorityKey();
    }

}