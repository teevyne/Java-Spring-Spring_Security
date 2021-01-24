package ro.cpatrut.auth.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.cpatrut.auth.entities.UserEntity;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface UserServiceRepository extends AuthorizationRepository<UserEntity, UUID> {

    boolean existsByUsername(final String username);

    boolean existsByEmailAddress(final String emailAddress);

    Optional<UserEntity> getByUsername(final String username);

    @Modifying
    @Query("update UserEntity u set " +
            "u.emailAddress=?1, u.firstName=?2, u.lastName=?3, u.username=?4" +
            " where u.id=?5")
    @Transactional
    int update(final String emailAddress,
               final String firstName,
               final String lastName,
               final String username,
               final UUID id);

    Stream<UserEntity> getAllByClientId(final String clientId, final Pageable pageable);

    @Transactional
    int deleteByUsername(final String username);

    Optional<UserEntity> getById(final UUID id);
}
