package ro.cpatrut.auth.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(schema = "users", name = "users")
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "is_account_non_expired", nullable = false)
    private final boolean isAccountExpired = false;

    @Column(name = "is_credentials_non_expired", nullable = false)
    private final boolean credentialsExpired = false;

    @Column(name = "is_account_non_locked")
    private final boolean accountLocked = false;

    @Column(name = "creation_time", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private ZonedDateTime updateTime;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "roles")
    private String userRoles;


}
