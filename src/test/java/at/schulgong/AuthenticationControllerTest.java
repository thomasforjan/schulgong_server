package at.schulgong;

import at.schulgong.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote Tests for {@link AuthenticationController} class and Test for Argon2 password encoder raw password to hashed password
 * @since June 2023
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTest {
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
