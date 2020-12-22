package fpoly.websitefpoly.controller;


import fpoly.websitefpoly.common.AppConstant;
import fpoly.websitefpoly.entity.AuthProvider;
import fpoly.websitefpoly.entity.Users;
import fpoly.websitefpoly.exception.BadRequestException;
import fpoly.websitefpoly.payload.ApiResponse;
import fpoly.websitefpoly.payload.AuthResponse;
import fpoly.websitefpoly.payload.LoginRequest;
import fpoly.websitefpoly.payload.SignUpRequest;
import fpoly.websitefpoly.repository.UserRepository;
import fpoly.websitefpoly.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        Optional<Users> users = userRepository.findByEmail(loginRequest.getEmail());
        if (users.isPresent()) {
            return ResponseEntity.ok(new AuthResponse(token, users.get().getRole()));
        }

        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        Users users = new Users();
        users.setName(signUpRequest.getName());
        users.setEmail(signUpRequest.getEmail());
        users.setPassword(signUpRequest.getPassword());
        users.setProvider(AuthProvider.local);
        users.setRole(AppConstant.STAFF);
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        Users result = userRepository.save(users);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

}
