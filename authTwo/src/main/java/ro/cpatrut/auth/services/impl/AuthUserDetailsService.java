package ro.cpatrut.auth.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.cpatrut.auth.entities.UserEntity;
import ro.cpatrut.auth.repositories.UserServiceRepository;

@Service("authUserDetailsService")
@Slf4j
public class AuthUserDetailsService implements UserDetailsService {

    private final UserServiceRepository userServiceRepository;
    private final ModelMapper modelMapper;

    public AuthUserDetailsService(final UserServiceRepository userServiceRepository, final ModelMapper modelMapper) {
        this.userServiceRepository = userServiceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("fetching user: " + username);
        final UserEntity userEntity = userServiceRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));


        final UserDetails user = User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .accountExpired(userEntity.isAccountExpired())
                .accountLocked(userEntity.isAccountLocked())
                .credentialsExpired(userEntity.isCredentialsExpired())
                .authorities(userEntity.getUserRoles())
                .build();
        return user;
    }
}
