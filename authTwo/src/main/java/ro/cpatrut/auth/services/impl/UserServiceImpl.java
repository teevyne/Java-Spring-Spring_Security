package ro.cpatrut.auth.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ro.cpatrut.auth.dto.UserTO;
import ro.cpatrut.auth.entities.UserEntity;
import ro.cpatrut.auth.repositories.UserServiceRepository;
import ro.cpatrut.auth.services.UserService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserServiceRepository userServiceRepository;
    private final ModelMapper modelMapper;
    private final ClientDetailsService clientDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserServiceRepository userServiceRepository,
                           final ModelMapper modelMapper,
                           final ClientDetailsService clientDetailsService,
                           final PasswordEncoder passwordEncoder) {
        this.userServiceRepository = userServiceRepository;
        this.modelMapper = modelMapper;
        this.clientDetailsService = clientDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserTO save(final UserTO user) {
        final ClientDetails clientDetails = clientDetailsService.loadClientByClientId(user.getClientId());
        if (clientDetails == null) {
            throw new BadCredentialsException("Unauthorized to save user");
        }
        final UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setUserRoles(getRoles(clientDetails));
        userEntity.setId(UUID.randomUUID());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userServiceRepository.save(userEntity), UserTO.UserTOBuilder.class).build();
    }


    @Override
    public int update(final UserTO user) {
        assertNotNull(user.getId(), "Id is null and it shouldn't");
        final String password = userServiceRepository.getById(user.getId()).orElseThrow(EntityNotFoundException::new).getPassword();
        if (passwordEncoder.matches(user.getOldPassword(), password)) {
            return userServiceRepository.update(user.getEmailAddress(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getId());
        }
        throw new BadCredentialsException("Password is incorrect");
    }

    @Override
    @Transactional
    public List<UserTO> getUsersForClient(final String clientId, final Pageable pageable) {
        return userServiceRepository.getAllByClientId(clientId, pageable)
                .map(entity -> modelMapper.map(entity, UserTO.UserTOBuilder.class).build())
                .collect(Collectors.toList());
    }


    private String getRoles(final ClientDetails clientDetails) {
        return StringUtils.collectionToCommaDelimitedString(clientDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
    }


}
