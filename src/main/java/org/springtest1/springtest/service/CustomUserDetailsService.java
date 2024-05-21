package org.springtest1.springtest.service;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springtest1.springtest.dao.entities.SessionData;
import org.springtest1.springtest.dao.entities.UserModel;
import org.springtest1.springtest.dao.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        SessionData session =  user.getSession();
        if (session == null) {
            throw new UsernameNotFoundException("No session data associated with the user: " + email);
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())

                .build();
    }
}

