package ro.cpatrut.auth.foundation.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import ro.cpatrut.auth.dto.UserTO;
import ro.cpatrut.auth.entities.UserEntity;
import ro.cpatrut.auth.foundation.SetupParent;
import ro.cpatrut.auth.repositories.UserServiceRepository;
import ro.cpatrut.auth.services.ClientService;
import ro.cpatrut.auth.services.UserService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.cpatrut.auth.api.UserController.USERS_PATH;
import static ro.cpatrut.auth.foundation.api.ClientControllerTest.DEFAULT_CLIENT_TO_BE_SAVED;

public class UserControllerTest extends SetupParent {

    private static final UserTO DEFAULT_USER_TO_BE_SAVED = UserTO.builder()
            .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
            .emailAddress("cpatrut@outlook.com")
            .username("cpatrut")
            .firstName("Catalin")
            .lastName("Patrut")
            .password("Passw0rd#")
            .build();

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Autowired
    private UserServiceRepository userServiceRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        clientService.save(DEFAULT_CLIENT_TO_BE_SAVED);
    }

    @After
    public void tearDown() {
        if (clientRegistrationService.listClientDetails().size() > 0) {
            clientRegistrationService.removeClientDetails(DEFAULT_CLIENT_TO_BE_SAVED.getClientId());
        }
        if (userServiceRepository.findAll().count() > 0) {
            userServiceRepository.deleteByUsername(DEFAULT_USER_TO_BE_SAVED.getUsername());
        }
        clientService = null;
        clientRegistrationService = null;
    }

    @Test
    public void succeedOnSavingUser() throws Exception {
        //Given & When
        mvcPerformer.performPost(USERS_PATH, DEFAULT_USER_TO_BE_SAVED)
                .andExpect(status().isCreated());

        //Then
        final List<UserEntity> users = userServiceRepository.findAll().collect(Collectors.toList());

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getClientId()).isEqualTo(DEFAULT_CLIENT_TO_BE_SAVED.getClientId());
        assertThat(users.get(0).getCreationTime()).isNotNull();
        assertThat(users.get(0).getUpdateTime()).isNotNull();
        assertThat(users.get(0).getEmailAddress()).isEqualTo(DEFAULT_USER_TO_BE_SAVED.getEmailAddress());
        assertThat(users.get(0).getUsername()).isEqualTo(DEFAULT_USER_TO_BE_SAVED.getUsername());
        assertThat(users.get(0).getFirstName()).isEqualTo(DEFAULT_USER_TO_BE_SAVED.getFirstName());
        assertThat(users.get(0).getLastName()).isEqualTo(DEFAULT_USER_TO_BE_SAVED.getLastName());
        assertThat(users.get(0).getLastName()).isNotEqualTo(DEFAULT_USER_TO_BE_SAVED.getPassword());
    }

    @Test
    public void failOnInsertingOwnCreationDate() throws Exception {
        mvcPerformer.performPost(USERS_PATH, UserTO.builder()
                .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
                .emailAddress("cpatrut@outlook.com")
                .username("cpatrut")
                .firstName("Catalin")
                .lastName("Patrut")
                .password("Passw0rd#")
                .creationTime(ZonedDateTime.now())
                .build()
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void failOnInsertingOwnUpdateTime() throws Exception {
        mvcPerformer.performPost(USERS_PATH, UserTO.builder()
                .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
                .emailAddress("cpatrut@outlook.com")
                .username("cpatrut")
                .firstName("Catalin")
                .lastName("Patrut")
                .password("Passw0rd#")
                .updateTime(ZonedDateTime.now())
                .build()
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingUserWithExistingUsername() throws Exception {
        mvcPerformer.performPost(USERS_PATH, DEFAULT_USER_TO_BE_SAVED)
                .andExpect(status().isCreated());

        mvcPerformer.performPost(USERS_PATH, UserTO.builder()
                .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
                .emailAddress("cpat2rut@outlook.com")
                .username(DEFAULT_USER_TO_BE_SAVED.getUsername())
                .firstName("Catalin")
                .lastName("Patrut")
                .password("Passw0rd#")
                .updateTime(ZonedDateTime.now())
                .build()
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void failOnSavingUserWithExistingEmailAddress() throws Exception {
        mvcPerformer.performPost(USERS_PATH, DEFAULT_USER_TO_BE_SAVED)
                .andExpect(status().isCreated());

        final String username = "cpatrut2";
        mvcPerformer.performPost(USERS_PATH, UserTO.builder()
                .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
                .emailAddress(DEFAULT_USER_TO_BE_SAVED.getEmailAddress())
                .username(username)
                .firstName("Catalin")
                .lastName("Patrut")
                .password("Passw0rd#")
                .updateTime(ZonedDateTime.now())
                .build()
        ).andExpect(status().isBadRequest());

        //cleanup
        userServiceRepository.deleteByUsername(username);
    }

    @Test
    public void succeedOnEncodingPassword() throws Exception {
        mvcPerformer.performPost(USERS_PATH, DEFAULT_USER_TO_BE_SAVED)
                .andExpect(status().isCreated());

        final List<UserTO> allByClientId = userService.getUsersForClient(
                DEFAULT_CLIENT_TO_BE_SAVED.getClientId(),
                PageRequest.of(0, 10));
        assertThat(allByClientId.get(0).getPassword()).isNotEqualTo(DEFAULT_USER_TO_BE_SAVED.getPassword());
    }

    @Test
    public void succeedOnUpdatingUser() throws Exception {
        //Given
        final UUID userId = userService.save(DEFAULT_USER_TO_BE_SAVED).getId();

        //When
        final String username = "newCpatrut";
        mvcPerformer.performPut(USERS_PATH, UserTO.builder()
                .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
                .id(userId)
                .emailAddress("newcpatrut@outlook.com")
                .username(username)
                .firstName("newCatalin")
                .lastName("newPatrut")
                .password("newPassw0rd#")
                .oldPassword(DEFAULT_USER_TO_BE_SAVED.getPassword())
                .build())
                .andExpect(status().isOk());
        //cleanup
        userServiceRepository.deleteByUsername(username);
    }

    @Test
    public void failOnUpdatingUserWithWrongPassword() throws Exception {
        //Given
        final UUID userId = userService.save(DEFAULT_USER_TO_BE_SAVED).getId();

        //When
        mvcPerformer.performPut(USERS_PATH, UserTO.builder()
                .clientId(DEFAULT_CLIENT_TO_BE_SAVED.getClientId())
                .id(userId)
                .emailAddress("newcpatrut@outlook.com")
                .username("newCpatrut")
                .firstName("newCatalin")
                .lastName("newPatrut")
                .password("newPassw0rd#")
                .oldPassword("wrong password")
                .build())
                .andExpect(status().isForbidden());
    }
}
