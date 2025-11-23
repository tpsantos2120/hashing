package org.java;

import java.util.Date;

/**
 * Main class demonstrating API key encoding and JWT signing with HS256 and HS512 algorithms.
 * <p>
 * This demonstration shows:
 * 1. Server-side secret key generation
 * 2. Encoding keys in different formats (Hex, Base64, Base64URL)
 * 3. End-user decoding and JWT signing
 * 4. Server-side JWT verification
 */
public class JwtHashingDemo {

  public static void main(String[] args) {
    System.out.println("=".repeat(80));
    System.out.println("API Key Encoding Demo: JWT Signing with HS256 vs HS512");
    System.out.println("=".repeat(80));
    System.out.println();

    // Sample payload data for JWT
    long currentTimeMillis = System.currentTimeMillis();
    Date issuedAt = new Date(currentTimeMillis);
    Date expiration = new Date(currentTimeMillis + 3600000); // 1 hour

    // Demonstrate HS256 with different encoding formats
    HS256ApiKeyDemo.demonstrate("user123", "jwt-demo", issuedAt, expiration);

    // Demonstrate HS512 with different encoding formats
    HS512ApiKeyDemo.demonstrate("user123", "jwt-demo", issuedAt, expiration);
  }
}
