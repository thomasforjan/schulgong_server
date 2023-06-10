package at.schulgong.controller;

import at.schulgong.dto.AuthResponseDTO;
import at.schulgong.dto.ConfigurationDTO;
import at.schulgong.dto.LoginDTO;
import at.schulgong.util.ReadWriteConfigurationFile;
import at.schulgong.util.SecretKeyService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.file.Paths;
import java.util.Date;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote AuthenticationController for handling authentication requests
 * @since June 2023
 */
@RestController
@CrossOrigin
public class AuthenticationController {

  Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
  private static final long EXPIRATION_TIME = 864000000; // 10 days
  private final SecretKey secretKey;
  private final String CONFIGURATION_FILE_PATH;

  /**
   * Constructor of AuthenticationController
   */
  public AuthenticationController(SecretKeyService secretKeyService) {
    this.secretKey = secretKeyService.getSecretKey();
    CONFIGURATION_FILE_PATH = Paths.get("src/main/resources/configuration.json").toAbsolutePath().toString();
  }

  /**
   * Method for handling authentication requests
   *
   * @param loginDTO LoginDTO
   * @return ResponseEntity<?>
   */
  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
    ConfigurationDTO configurationDTO = ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(CONFIGURATION_FILE_PATH);

    if (configurationDTO == null) {
      return ResponseEntity.status(500).body("Server-Konfigurationsdatei konnte nicht gelesen werden. Bitte kontaktieren Sie den Systemadministrator.");
    }

    boolean isPasswordMatch = argon2PasswordEncoder.matches(loginDTO.getPassword(), configurationDTO.getPassword());

    if (isPasswordMatch) {
      String token = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject("user")
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(this.secretKey)
        .compact();

      return ResponseEntity.ok(new AuthResponseDTO(token));
    } else {
      return ResponseEntity.status(401).body("Fehlerhafte Anmeldung. Bitte überprüfen Sie Ihr Passwort.");
    }
  }

  /**
   * Method for hashing a password with Argon2
   *
   * @param password Password in plain text
   * @return Argon2 hashed password
   */
  @PostMapping("/hash")
  public String hashPassword(@RequestParam("password") String password) {
    return argon2PasswordEncoder.encode(password);
  }

  /**
   * Method for verifying the JWT token
   *
   * @param request HttpServletRequest
   * @return ResponseEntity<?>
   */
  @GetMapping("/verifyToken")
  public ResponseEntity<?> verifyToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    if (token == null) {
      return ResponseEntity.status(401).body("Kein Token bereitgestellt.");
    }

    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      return ResponseEntity.status(401).body("Token ist abgelaufen.");
    } catch (JwtException e) {
      return ResponseEntity.status(401).body("Token ist ungültig.");
    }

    return ResponseEntity.ok("Token ist gültig.");
  }
}

