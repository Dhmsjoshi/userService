package dev.dharam.userservice.security.service;

import dev.dharam.userservice.entities.User;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import dev.dharam.userservice.repositories.UserRepository;
import dev.dharam.userservice.security.models.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException(username+" does nor found!")
        );

        return new CustomUserDetail(user);
    }
}
