package ro.cpatrut.auth.services;

import org.springframework.data.domain.Pageable;
import ro.cpatrut.auth.dto.UserTO;

import java.util.List;

public interface UserService {

    UserTO save(final UserTO user);

    int update(UserTO user);

    List<UserTO> getUsersForClient(String clientId, final Pageable pageable);

}
