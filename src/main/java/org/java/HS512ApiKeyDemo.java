package org.java;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Demonstrates API key distribution and JWT signing/verification using HS512 algorithm.
 */
public class HS512ApiKeyDemo {

  /**
   * Demonstrates the complete API key distribution flow for HS512.
   *
   * @param subject    JWT subject
   * @param issuer     JWT issuer
   * @param issuedAt   JWT issued timestamp
   * @param expiration JWT expiration timestamp
   */
  public static void demonstrate(String subject, String issuer, Date issuedAt, Date expiration) {
    System.out.println("2. HS512 (HMAC-SHA512) - API Key Distribution Demonstration");
    System.out.println("=".repeat(80));
    System.out.println();

    // Server: Generate a secret key (512 bits minimum for HS512)
    SecretKey secretKey = SecretKeyGenerator.generateHS512Key();
    byte[] keyBytes = secretKey.getEncoded();

    System.out.println("SERVER SIDE: Generated Secret Key");
    System.out.println("-".repeat(80));
    System.out.println(
        "Key Size: " + (keyBytes.length * 8) + " bits (" + keyBytes.length + " bytes)");
    System.out.println("Algorithm: HS512 (HMAC-SHA512)");
    System.out.println();

    // Show the key in different encoding formats
    KeyEncodingUtils.displayKeyEncodings(keyBytes);

    // Simulate end user using each encoding format
    demonstrateEndUserFlow("Hex", KeyEncodingUtils.encodeToHex(keyBytes),
        subject, issuer, issuedAt, expiration, secretKey);
    demonstrateEndUserFlow("Base64", KeyEncodingUtils.encodeToBase64(keyBytes),
        subject, issuer, issuedAt, expiration, secretKey);
    demonstrateEndUserFlow("Base64URL", KeyEncodingUtils.encodeToBase64Url(keyBytes),
        subject, issuer, issuedAt, expiration, secretKey);

    System.out.println();
  }

  /**
   * Demonstrates the end user flow: receiving encoded key, decoding, signing JWT, and server
   * verification.
   */
  private static void demonstrateEndUserFlow(String encodingType,
                                             String encodedKey,
                                             String subject,
                                             String issuer,
                                             Date issuedAt,
                                             Date expiration,
                                             SecretKey originalKey) {
    System.out.println("END USER FLOW - " + encodingType + " Encoding:");
    System.out.println("-".repeat(80));

    // End user reconstructs the key from the encoded string
    byte[] decodedKeyBytes = KeyEncodingUtils.decodeKey(encodedKey, encodingType);
    SecretKey reconstructedKey = new SecretKeySpec(decodedKeyBytes, "HmacSHA512");

    System.out.println("  Step 1: Received encoded key (" + encodingType + ")");
    System.out.println("  Step 2: Decoded key to binary (" + decodedKeyBytes.length + " bytes)");
    System.out.println("  Step 3: Creating and signing JWT...");

    // End user creates and signs JWT
    String jwt = JwtUtils.createAndSignJwt(subject, issuer, issuedAt, expiration,
        reconstructedKey);

    System.out.println("  Generated JWT: " + jwt.substring(0, Math.min(50, jwt.length())) + "...");
    System.out.println("  Full JWT Length: " + jwt.length() + " characters");
    System.out.println();

    // Server verifies the JWT
    System.out.println("SERVER SIDE - Verification:");
    Claims claims = JwtUtils.verifyAndGetClaims(jwt, originalKey);

    if (claims != null) {
      System.out.println("  Subject: " + claims.getSubject());
      System.out.println("  Issuer: " + claims.getIssuer());
      System.out.println("  Role: " + claims.get("role"));
      System.out.println("  Email: " + claims.get("email"));
      System.out.println("  JWT Verification: ✓ SUCCESS");
    } else {
      System.out.println("  JWT Verification: ✗ FAILED");
    }
    System.out.println();
  }
}
