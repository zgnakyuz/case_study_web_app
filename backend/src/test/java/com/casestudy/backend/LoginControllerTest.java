package com.casestudy.backend;

import com.casestudy.backend.login.LoginController;
import com.casestudy.backend.login.LoginRequest;
import com.casestudy.backend.role.RoleRepository;
import com.casestudy.backend.security.jwt.JwtResponse;
import com.casestudy.backend.security.jwt.JwtUtils;
import com.casestudy.backend.security.services.UserDetailsImpl;
import com.casestudy.backend.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateUser_Success() {
        // Mock the LoginRequest
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPassword");

        // Mock Authentication
        Authentication authentication = mock(Authentication.class);

        // Mock UserDetailsImpl
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testUser", "test@example.com", "testPassword", 5, Collections.emptyList());

        // Mock JWT Token
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mockedJWTToken");

        // Call the authenticateUser method
        ResponseEntity<?> response = loginController.authenticateUser(loginRequest);

        // Assertions
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof JwtResponse);
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("mockedJWTToken", jwtResponse.getAccessToken());
    }
}