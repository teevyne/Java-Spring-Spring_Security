package ro.cpatrut.auth.foundation.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import ro.cpatrut.auth.api.ClientController;
import ro.cpatrut.auth.dto.ClientTO;
import ro.cpatrut.auth.foundation.SetupParent;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.cpatrut.auth.services.converters.ClientTOConverter.ADDITIONAL_INFORMATION_KEY;

public class ClientControllerTest extends SetupParent {

    static final ClientTO DEFAULT_CLIENT_TO_BE_SAVED = ClientTO.builder()
            .clientId("my-trusted-client")
            .clientSecret("Passw0rd#")
            .accessTokenValidity(123232)
            .refreshTokenValidity(2313131)
            .resourcedIds(null)
            .scope("read,write")
            .authorities("ADMIN,USER")
            .authorizedGrantTypes("password,refresh_token")
            .additionalInformation("great client")
            .autoApproveScopes(null)
            .build();

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        if (clientRegistrationService.listClientDetails().size() > 0) {
            clientRegistrationService.removeClientDetails(DEFAULT_CLIENT_TO_BE_SAVED.getClientId());
        }
        clientRegistrationService = null;
    }


    @Test
    public void succeedOnSavingClientDetails() throws Exception {
        //Given & When
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, DEFAULT_CLIENT_TO_BE_SAVED)
                .andExpect(status().isCreated());

        //Then
        final List<ClientDetails> clientDetails = clientRegistrationService.listClientDetails();

        assertThat(clientDetails.size()).isEqualTo(1);
        final ClientDetails clientDetail = clientDetails.get(0);

        assertThat(clientDetail.getClientId()).isEqualTo(DEFAULT_CLIENT_TO_BE_SAVED.getClientId());
        assertThat(clientDetail.getResourceIds()).isEmpty();
        assertThat(clientDetail.getClientSecret()).isNotNull();
        assertThat(clientDetail.getScope().size()).isEqualTo(2);
        assertThat(clientDetail.getScope()).contains("read");
        assertThat(clientDetail.getScope()).contains("write");
        assertThat(clientDetail.getAuthorizedGrantTypes().size()).isEqualTo(2);
        assertThat(clientDetail.getAuthorizedGrantTypes()).contains("password", "refresh_token");
        assertThat(clientDetail.getAccessTokenValiditySeconds()).isEqualTo(DEFAULT_CLIENT_TO_BE_SAVED.getAccessTokenValidity());
        assertThat(clientDetail.getRefreshTokenValiditySeconds()).isEqualTo(DEFAULT_CLIENT_TO_BE_SAVED.getRefreshTokenValidity());
        assertThat(clientDetail.getAuthorities().size()).isEqualTo(2);
        assertThat(clientDetail.getAuthorities()).contains(new SimpleGrantedAuthority("ADMIN"),
                new SimpleGrantedAuthority("USER"));
        assertThat(clientDetail.getAdditionalInformation().get(ADDITIONAL_INFORMATION_KEY)).isEqualTo(DEFAULT_CLIENT_TO_BE_SAVED.getAdditionalInformation());
    }

    @Test
    public void failOnSavingClientWithSameId() throws Exception {
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, DEFAULT_CLIENT_TO_BE_SAVED)
                .andExpect(status().isCreated());
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, DEFAULT_CLIENT_TO_BE_SAVED)
                .andExpect(status().isConflict());
    }

    @Test
    public void failOnSavingClientWithoutId() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMIN,USER")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithIdLessThan5Chars() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("1234")
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMIN,USER")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithInvalidPassword() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("123456")
                .clientSecret("Passw0rd")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMIN,USER")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithNoPwd() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("123456")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMIN,USER")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithNoScope() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("123456")
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .authorities("ADMIN,USER")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithNoGrantTypes() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("123456")
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMIN,USER")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithInvalidGrantType() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("123456")
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMIN,USER")
                .authorizedGrantTypes("13.,.4532")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithNoAuthorities() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("my-trusted-client")
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
//                .authorities("ADMIN,USER")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingClientWithInvalidAuthorities() throws Exception {
        //Given
        final ClientTO client = ClientTO.builder()
                .clientId("my-trusted-client")
                .clientSecret("Passw0rd#")
                .accessTokenValidity(123232)
                .refreshTokenValidity(2313131)
                .resourcedIds(null)
                .scope("read,write")
                .authorities("ADMINUSER, george")
                .authorizedGrantTypes("password,refreshToken")
                .additionalInformation("great client")
                .autoApproveScopes(null)
                .build();
        //When & Then
        mvcPerformer.performPost(ClientController.CLIENTS_PATH, client)
                .andExpect(status().isBadRequest());
    }


}