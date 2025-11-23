package org.java;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for JWT operations including signing and verification.
 */
public class JwtUtils {

  /**
   * Creates and signs a JWT with the provided parameters.
   *
   * @param subject    The subject of the JWT (typically user ID)
   * @param issuer     The issuer of the JWT
   * @param issuedAt   The timestamp when the JWT was issued
   * @param expiration The expiration timestamp
   * @param secretKey  The secret key to sign with
   * @return The signed JWT string
   */
  public static String createAndSignJwt(String subject,
                                        String issuer,
                                        Date issuedAt,
                                        Date expiration,
                                        SecretKey secretKey) {
    return Jwts.builder()
        .subject(subject)
        .issuer(issuer)
        .issuedAt(issuedAt)
        .expiration(expiration)
        .claim("role", "user")
        .claim("email", "user@example.com")
        .signWith(secretKey)
        .compact();
  }

  /**
   * Verifies a JWT signature and extracts claims.
   *
   * @param jwt       The JWT string to verify
   * @param secretKey The secret key to verify with
   * @return true if verification succeeds, false otherwise
   */
  public static boolean verifyJwt(String jwt, SecretKey secretKey) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(jwt)
          .getPayload();

      System.out.println("  Subject: " + claims.getSubject());
      System.out.println("  Issuer: " + claims.getIssuer());
      System.out.println("  Role: " + claims.get("role"));
      System.out.println("  Email: " + claims.get("email"));
      return true;
    } catch (Exception e) {
      System.out.println("  Error: " + e.getMessage());
      return false;
    }
  }

  /**
   * Verifies a JWT and returns the claims without printing.
   *
   * @param jwt       The JWT string to verify
   * @param secretKey The secret key to verify with
   * @return The claims if successful, null otherwise
   */
  public static Claims verifyAndGetClaims(String jwt, SecretKey secretKey) {
    try {
      return Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(jwt)
          .getPayload();
    } catch (Exception e) {
      return null;
    }
  }
}
