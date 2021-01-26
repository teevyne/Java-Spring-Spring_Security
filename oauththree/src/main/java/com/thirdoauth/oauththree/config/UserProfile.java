package com.thirdoauth.oauththree.config;

import lombok.Data;

@Data
public class UserProfile
{
    private String name;
    private String email;

    //Setters and getters

    @Override
    public String toString() {
        return "UserProfile [name=" + name + ", email=" + email + "]";
    }
}
