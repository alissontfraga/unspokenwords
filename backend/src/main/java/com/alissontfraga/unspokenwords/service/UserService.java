package com.alissontfraga.unspokenwords.service;

import com.alissontfraga.unspokenwords.model.User;
import com.alissontfraga.unspokenwords.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){this.userRepository = userRepository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities("USER")
                .build();
    }

    public User createUser(String username, String rawPassword, BCryptPasswordEncoder encoder) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(rawPassword));
        return userRepository.save(u);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    //only postman
    public void deleteByUsername(String username) {
        User user = findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
        }
    }


}

