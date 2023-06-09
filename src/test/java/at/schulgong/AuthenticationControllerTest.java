package at.schulgong;

import at.schulgong.controller.AuthenticationController;
import at.schulgong.dto.ConfigurationDTO;
import at.schulgong.dto.LoginDTO;
import at.schulgong.util.SecretKeyService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Tests for {@link AuthenticationController} class and Test for Argon2 password encoder raw password to hashed password
 * @since June 2023
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTest {

  @InjectMocks
  AuthenticationController authenticationController;

  @Mock
  Argon2PasswordEncoder argon2PasswordEncoder;

  @Mock
  private SecretKeyService secretKeyService;

  SecretKey secretKey;

  @Mock
  ConfigurationDTO configurationDTO;

  @Mock
  LoginDTO loginDTO;

  /**
   * Setup the mocks
   */
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    when(secretKeyService.getSecretKey()).thenReturn(secretKey);
    authenticationController = new AuthenticationController(secretKeyService);
  }

  /**
   * Test if the authentication works if the password is correct
   */
  @Test
  void testAuthenticate() {
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    when(secretKeyService.getSecretKey()).thenReturn(secretKey);

    when(loginDTO.getPassword()).thenReturn("Schulgong");
    when(configurationDTO.getPassword()).thenReturn("$argon2id$v=19$m=60000,t=10,p=1$E4W+ArwtCGSfnrX3DTSJ6g$V2fBKFzAjHy+zhr3t7fHMnVGY1XnFYPFAuTbP3g/Lcs");
    when(argon2PasswordEncoder.matches(loginDTO.getPassword(), configurationDTO.getPassword())).thenReturn(true);

    ResponseEntity<?> result = authenticationController.authenticate(loginDTO);

    assertEquals(HttpStatus.OK, result.getStatusCode());
  }


  /**
   * Test if the authentication fails if the password is wrong
   */
  @Test
  void testAuthenticate_Failed() {
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    when(secretKeyService.getSecretKey()).thenReturn(secretKey);

    when(loginDTO.getPassword()).thenReturn("wrongPassword");
    when(configurationDTO.getPassword()).thenReturn("$argon2id$v=19$m=60000,t=10,p=1$VpM7WJo3vSg5J31VLs74GA$e/t1NrAAYKPuDTT9TIgHzMT8zXu6x5mbNLkspVzQjUk");
    when(argon2PasswordEncoder.matches(loginDTO.getPassword(), configurationDTO.getPassword())).thenReturn(false);

    ResponseEntity<?> result = authenticationController.authenticate(loginDTO);

    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }

  /**
   * Test if the password is hashed correctly
   */
  @Test
  void testHashPasswordMethodFromAuthenticationController() {
    String password = "Schulgong";

    String hashedPassword = authenticationController.hashPassword(password);

    when(argon2PasswordEncoder.matches(password, hashedPassword)).thenAnswer(i -> true);

    assertTrue(argon2PasswordEncoder.matches(password, hashedPassword));
  }


  /**
   * Test for Argon2 password encoder raw password to hashed password
   */
  @Test
  void testRawPasswordToHashedPassword() {
    String rawPassword = "Schulgong";
    Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);

    String hashedPassword = argon2PasswordEncoder.encode(rawPassword);

    assertTrue(argon2PasswordEncoder.matches(rawPassword, hashedPassword));
  }
}
