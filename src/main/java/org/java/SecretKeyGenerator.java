package org.java;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

/**
 * Utility class for generating cryptographically secure secret keys.
 */
public class SecretKeyGenerator {

  /**
   * Generates a secret key suitable for HS256 (256 bits = 32 bytes).
   *
   * @return A SecretKey for HS256 algorithm
   */
  public static SecretKey generateHS256Key() {
    return Jwts.SIG.HS256.key().build();
  }

  /**
   * Generates a secret key suitable for HS512 (512 bits = 64 bytes).
   *
   * @return A SecretKey for HS512 algorithm
   */
  public static SecretKey generateHS512Key() {
    return Jwts.SIG.HS512.key().build();
  }
}
