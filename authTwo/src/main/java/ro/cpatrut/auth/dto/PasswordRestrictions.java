package ro.cpatrut.auth.dto;

public interface PasswordRestrictions {
    String REGEX_ENFORCER = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";
    String ERROR_MESSAGE_PASSWORD = "Password must contain eight characters, at least one letter, one number and one special character";

}
