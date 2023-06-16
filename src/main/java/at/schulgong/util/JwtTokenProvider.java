package at.schulgong.util;

import at.schulgong.dto.ConfigurationDTO;
import at.schulgong.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote JwtTokenProvider is a provider, which provides the token and validates it
 * @since June 2023
 */
@Service
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final ConfigurationDTO configurationDTO;
    private final String CONFIGURATION_FILE_PATH;

    /**
     * Constructor
     *
     * @param secretKeyService SecretKeyService
     */
    public JwtTokenProvider(SecretKeyService secretKeyService) {
        this.secretKey = secretKeyService.getSecretKey();
        CONFIGURATION_FILE_PATH =
                Paths.get(Config.CONFIGURATION_PATH.getPath()).toAbsolutePath().toString();
        this.configurationDTO =
                ReadWriteConfigurationFile.readConfigurationDTOFromConfigFile(
                        CONFIGURATION_FILE_PATH);
    }

    /**
     * Validate the JSON Web Token
     *
     * @param token JSON Web Token
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims =
                    Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    /**
     * Get the JWT token by extracting it from Bearer token
     *
     * @param request HttpServletRequest
     * @return String
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Check if the user is authenticated
     *
     * @return boolean
     */
    public Authentication getAuthentication() {
        UserDetails userDetails =
                new User("user", "", Collections.singletonList(new SimpleGrantedAuthority("USER")));
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }
}
