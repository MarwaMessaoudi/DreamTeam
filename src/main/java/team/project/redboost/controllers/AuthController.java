package team.project.redboost.controllers;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.project.redboost.authentif.JwtUtil;
import team.project.redboost.entities.Role;
import team.project.redboost.entities.User;
import team.project.redboost.services.CustomUserDetailsService;
import team.project.redboost.services.FirebaseService;
import team.project.redboost.services.UserService;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService; // Use UserService directly

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/oauth/login")
    public ResponseEntity<?> oauthLogin(@RequestBody Map<String, String> oauthRequest) {
        String provider = oauthRequest.get("provider");
        String providerId = oauthRequest.get("providerId");
        log.info("Authenticating user with provider: {} and providerId: {}", provider, providerId);

        try {
            // Load user details by provider ID
            final UserDetails userDetails = customUserDetailsService.loadUserByProviderId(providerId);

            // Fetch user entity from DB
            User user = userService.findByProviderId(providerId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "message", "User not found with provider ID: " + providerId,
                        "errorCode", "AUTH006"
                ));
            }

            // Generate JWT tokens with provider and providerId
            final String accessToken = jwtUtil.generateToken(userDetails.getUsername(), provider, providerId, userDetails.getAuthorities());
            final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername(), userDetails.getAuthorities());

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "roles", userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()),
                    "user", user
            ));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", e.getMessage(),
                    "errorCode", "AUTH007"
            ));
        }
    }

    @PostMapping("/firebase")
    public ResponseEntity<?> firebaseLogin(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");

        try {
            FirebaseToken decodedToken = firebaseService.verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String uid = decodedToken.getUid();

            // Check if the user already exists in your database
            User user = userService.findByEmail(email);
            if (user == null) {
                // Create a new user if they don't exist
                user = new User();
                user.setEmail(email);
                user.setProvider("google");
                user.setProviderId(uid);
                user.setRole(Role.USER); // Assign default role
                userService.addUser(user);
            }

            // Generate JWT tokens
            final String accessToken = jwtUtil.generateToken(user.getEmail(), user.getAuthorities());
            final String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getAuthorities());

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()),
                    "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid ID token"));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        log.info("Authenticating user with email: {}", email);

        try {
            // Authenticate user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // Load user details (which includes authorities/roles)
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Get authorities (roles) from UserDetails
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // Fetch user entity from DB
            User user = userService.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "message", "User not found with email: " + email,
                        "errorCode", "AUTH008"
                ));
            }

            // Generate JWT tokens without provider and providerId
            final String accessToken = jwtUtil.generateToken(userDetails.getUsername(), authorities);
            final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername(), authorities);

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "roles", authorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()),
                    "user", user
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", e.getMessage(),
                    "errorCode", "AUTH009"
            ));
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        // Extract the refresh token from the request
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Refresh token is missing!",
                    "errorCode", "AUTH003"
            ));
        }

        // Extract email from the refresh token
        String email;
        try {
            email = jwtUtil.extractEmail(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid refresh token format!",
                    "errorCode", "AUTH004"
            ));
        }

        // Get the roles (authorities) from the user details
        Collection<? extends GrantedAuthority> authorities = userDetailsService.loadUserByUsername(email).getAuthorities();

        // Validate the refresh token
        if (!jwtUtil.validateToken(refreshToken, email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid or expired refresh token!",
                    "errorCode", "AUTH005"
            ));
        }

        // Generate a new access token
        String newAccessToken = jwtUtil.generateToken(email, authorities);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }





    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> registrationRequest) {
        String email = registrationRequest.get("email");
        String password = registrationRequest.get("password");
        String firstName = registrationRequest.get("firstName");
        String lastName = registrationRequest.get("lastName");
        String phoneNumber = registrationRequest.get("phoneNumber");
        Role role = Role.valueOf(registrationRequest.get("role"));
        // Validate required fields
        if (email == null || password == null || firstName == null || lastName == null || phoneNumber == null || role == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "All fields (email, password, firstName, lastName, phoneNumber) are required!",
                    "errorCode", "AUTH010"
            ));
        }

        // Check if the user already exists
        if (userService.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "message", "User with this email already exists!",
                    "errorCode", "AUTH011"
            ));
        }

        // Create a new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // Password will be hashed in the service layer
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setRole(Role.USER); // Assign default role

        // Save the user
        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User registered successfully!",
                "user", user
        ));
    }




}