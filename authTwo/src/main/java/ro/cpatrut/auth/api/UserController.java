package ro.cpatrut.auth.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cpatrut.auth.dto.UserTO;
import ro.cpatrut.auth.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(UserController.USERS_PATH)
@Slf4j
public class UserController {
    public static final String USERS_PATH = "/users";
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserTO>> get(@NotNull @RequestParam("page") final int page,
                                            @NotNull @RequestParam("size") final int size) {
        log.debug("getting users");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserTO> save(@Valid @RequestBody final UserTO user) {
        log.debug("saving user" + user);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody final UserTO user) {
        log.debug("updating user" + user);
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
