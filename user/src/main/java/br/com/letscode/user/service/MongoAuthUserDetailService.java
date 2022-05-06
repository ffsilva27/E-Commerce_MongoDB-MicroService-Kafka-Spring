package br.com.letscode.user.service;

import br.com.letscode.user.model.Authority;
import br.com.letscode.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MongoAuthUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<br.com.letscode.user.model.User> user = userRepository.findById(userName);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<Authority> authorityList = new ArrayList<>();

        user.get().getRoles()
                .forEach(role -> {
                    authorityList.add(new Authority(role.getAuthority()));
                });

        return new User(user.get().getUsername(), user.get().getPassword(), authorityList);
    }

}