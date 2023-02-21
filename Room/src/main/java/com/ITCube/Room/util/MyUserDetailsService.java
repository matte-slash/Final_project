package com.ITCube.Room.util;

import com.ITCube.Data.model.MyUserPrincipal;
import com.ITCube.Data.model.User;
import com.ITCube.Data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Matteo Rosso
 */
@Service
@EnableJpaRepositories(basePackages = "com.ITCube.Data.repository")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository rep;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = rep.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}
