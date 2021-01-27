package com.iwiseoauth.springbootoauthjwt.service;

import com.iwiseoauth.springbootoauthjwt.model.User;
import com.iwiseoauth.springbootoauthjwt.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> findById(String username){
        return  userRepo.findById(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findById(username);

        if(user.isPresent()){
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
                    getAuthority(user.get().getRole()));
        }else throw new UsernameNotFoundException("User " + username + " was not found in the database");

    }

    private static List<SimpleGrantedAuthority> getAuthority(String role) {

        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
    }

}
