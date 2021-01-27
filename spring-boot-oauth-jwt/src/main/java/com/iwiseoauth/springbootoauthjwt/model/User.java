package com.iwiseoauth.springbootoauthjwt.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class User {

    @Id
    private String username;

    private String password;

    private String role;


}
