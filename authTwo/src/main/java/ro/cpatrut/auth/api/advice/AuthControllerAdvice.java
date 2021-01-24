package ro.cpatrut.auth.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<String> entityNotFoundResponse() {
        return new ResponseEntity<>("Client identifier already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> validationError() {
        return new ResponseEntity<>("Validation didn't pass, check our docs", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> unauthorized() {
        return new ResponseEntity<>("Unauthorized to perform action", HttpStatus.FORBIDDEN);
    }

}
