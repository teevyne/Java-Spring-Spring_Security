package ro.cpatrut.auth.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import ro.cpatrut.auth.dto.ClientTO;
import ro.cpatrut.auth.services.ClientService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRegistrationService clientRegistrationService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(final ClientRegistrationService clientRegistrationService,
                             final ModelMapper modelMapper,
                             final PasswordEncoder passwordEncoder) {
        this.clientRegistrationService = clientRegistrationService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(final ClientTO client) {
        final BaseClientDetails baseClientDetails = modelMapper.map(client, BaseClientDetails.class);
        baseClientDetails.setClientSecret(passwordEncoder.encode(baseClientDetails.getClientSecret()));
        clientRegistrationService.addClientDetails(baseClientDetails);
    }

    @Override
    public void update(final ClientTO client) {
        final BaseClientDetails baseClientDetails = modelMapper.map(client, BaseClientDetails.class);
        clientRegistrationService.updateClientDetails(baseClientDetails);
    }

    @Override
    public List<ClientTO> getAll() {
        return clientRegistrationService.listClientDetails().stream()
                .map(clientDetails -> modelMapper.map(new BaseClientDetails(clientDetails), ClientTO.class))
                .collect(Collectors.toList());
    }


}
