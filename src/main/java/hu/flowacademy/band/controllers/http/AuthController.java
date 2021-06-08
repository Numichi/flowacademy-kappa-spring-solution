package hu.flowacademy.band.controllers.http;

import hu.flowacademy.band.controllers.requests.LoginRequest;
import hu.flowacademy.band.controllers.responses.RegistrationRequest;
import hu.flowacademy.band.database.models.Role;
import hu.flowacademy.band.database.models.User;
import hu.flowacademy.band.database.repository.RoleRepository;
import hu.flowacademy.band.database.repository.UserRepository;
import hu.flowacademy.band.enums.Roles;
import hu.flowacademy.band.exceptions.WrongEmailOrPasswordException;
import hu.flowacademy.band.services.auth.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Idő hiányában nem csináltam külön Service-t az alábbi controllernek...
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    AuthController(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder,
        JwtProvider jwtProvider
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@RequestBody @Valid @NonNull RegistrationRequest registration) {
        var user = User.builder()
            .email(registration.getEmail())
            .password(passwordEncoder.encode(registration.getPassword()))
            .build();

        user = userRepository.save(user);

        // Most minden registrált admin is lesz!
        var role = Role.builder().role(Roles.ROLE_ADMIN.name()).user(user).build();

        roleRepository.save(role);
    }

    /**
     * Ha nem sikerül a login, akkor Exception. Majd az ErrorController lekezeli. :)
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping("login")
    public String login(@RequestBody @Valid @NonNull LoginRequest login) {
        var user = userRepository.findByEmail(login.getEmail()).orElseThrow(WrongEmailOrPasswordException::new);

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new WrongEmailOrPasswordException();
        }

        return jwtProvider.generate(user);
    }
}
