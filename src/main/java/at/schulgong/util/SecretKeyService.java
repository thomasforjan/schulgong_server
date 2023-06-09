package at.schulgong.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote SecretKeyService is a service, which provides the secret key
 * @since June 2023
 */
@Service
public class SecretKeyService {

  private final SecretKey secretKey;

  /**
   * Constructor
   */
  public SecretKeyService() {
    this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  /**
   * Get the secret key
   *
   * @return SecretKey
   */
  public SecretKey getSecretKey() {
    return this.secretKey;
  }
}
