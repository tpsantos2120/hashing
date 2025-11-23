# JWT API Key Authentication Demo

A comprehensive Java demonstration project showcasing **JWT (JSON Web Token)** signing and verification using **HMAC-based algorithms** (HS256 and HS512) with multiple key encoding formats.

## Table of Contents

- [Overview](#overview)
- [What This Project Demonstrates](#what-this-project-demonstrates)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [How It Works](#how-it-works)
- [Key Concepts](#key-concepts)
- [Security Considerations](#security-considerations)
- [Example Output](#example-output)
- [Use Cases](#use-cases)

---

## Overview

This project demonstrates the complete lifecycle of API key-based JWT authentication, simulating both **server-side** and **client-side** operations. It shows how to:

1. Generate cryptographically secure secret keys
2. Encode keys in different formats for distribution (Hex, Base64, Base64URL)
3. Decode keys on the client side
4. Sign JWTs with the decoded keys
5. Verify JWTs on the server side

The demonstration compares two popular HMAC algorithms: **HS256 (HMAC-SHA256)** and **HS512 (HMAC-SHA512)**.

---

## What This Project Demonstrates

### Core Functionality

- **Secret Key Generation**: Uses JJWT's modern API (`Jwts.SIG.HS256.key().build()`) for cryptographically secure key generation
- **Key Encoding**: Supports three encoding formats:
  - **Hex**: Human-readable hexadecimal (64/128 characters for HS256/HS512)
  - **Base64**: Standard Base64 encoding with padding (44/88 characters)
  - **Base64URL**: URL-safe Base64 without padding (43/86 characters)
- **JWT Lifecycle**: Complete JWT creation, signing, and verification workflow
- **Algorithm Comparison**: Side-by-side comparison of HS256 vs HS512

### Educational Value

- Demonstrates best practices for JWT signing with symmetric keys
- Shows the difference between key encoding formats and when to use each
- Illustrates the complete API key distribution workflow
- Provides hands-on examples of JJWT 0.12.x modern API

---

## Project Structure

```
hashing/
├── pom.xml                           # Maven configuration
├── README.md                         # This file
└── src/main/
    ├── java/org/java/
    │   ├── JwtHashingDemo.java       # Main entry point
    │   ├── HS256ApiKeyDemo.java      # HS256 demonstration
    │   ├── HS512ApiKeyDemo.java      # HS512 demonstration
    │   ├── SecretKeyGenerator.java   # Key generation utility
    │   ├── KeyEncodingUtils.java     # Encoding/decoding utilities
    │   └── JwtUtils.java             # JWT creation and verification
    └── resources/
        └── application.properties     # Configuration (optional)
```

### Component Breakdown

#### 1. **JwtHashingDemo.java**
- **Purpose**: Main entry point for the application
- **What it does**: Orchestrates the demonstration by calling HS256 and HS512 demos
- **Key features**:
  - Sets up sample JWT payload (subject, issuer, timestamps)
  - Runs demonstrations for both algorithms sequentially

#### 2. **HS256ApiKeyDemo.java**
- **Purpose**: Demonstrates HS256 (HMAC-SHA256) algorithm
- **What it does**:
  - Generates a 256-bit (32-byte) secret key
  - Shows the key in all three encoding formats
  - Simulates end-user flow for each encoding
  - Verifies JWTs on the server side
- **Key features**:
  - Server-side key generation
  - End-user key decoding and JWT signing
  - Server-side JWT verification

#### 3. **HS512ApiKeyDemo.java**
- **Purpose**: Demonstrates HS512 (HMAC-SHA512) algorithm
- **What it does**: Same as HS256 but with 512-bit (64-byte) keys
- **Key features**:
  - Stronger security with longer keys
  - Demonstrates higher entropy for sensitive applications

#### 4. **SecretKeyGenerator.java**
- **Purpose**: Utility for generating cryptographically secure keys
- **What it does**:
  - Generates keys using JJWT's modern API
  - Uses `Jwts.SIG.HS256.key().build()` and `Jwts.SIG.HS512.key().build()`
- **Methods**:
  - `generateHS256Key()`: Returns a 256-bit SecretKey for HS256
  - `generateHS512Key()`: Returns a 512-bit SecretKey for HS512
- **Security**: Uses `SecureRandom` internally via JJWT

#### 5. **KeyEncodingUtils.java**
- **Purpose**: Encodes and decodes keys in multiple formats
- **What it does**:
  - Converts binary key bytes to/from Hex, Base64, and Base64URL
  - Displays formatted output for comparison
- **Methods**:
  - `encodeToHex()`: Converts bytes to lowercase hexadecimal
  - `encodeToBase64()`: Converts bytes to Base64 with padding
  - `encodeToBase64Url()`: Converts bytes to URL-safe Base64 without padding
  - `decodeKey()`: Decodes from any format back to binary
  - `displayKeyEncodings()`: Shows all three formats for comparison

#### 6. **JwtUtils.java**
- **Purpose**: JWT creation and verification utilities
- **What it does**:
  - Creates signed JWTs with custom claims
  - Verifies JWT signatures
  - Extracts claims from valid JWTs
- **Methods**:
  - `createAndSignJwt()`: Builds and signs a JWT with the provided key
  - `verifyJwt()`: Verifies JWT signature and prints claims
  - `verifyAndGetClaims()`: Verifies and returns claims without printing

---

## Requirements

- **Java**: 21 or higher
- **Maven**: 3.6+ (for building)
- **Dependencies**:
  - JJWT API 0.12.5
  - JJWT Implementation 0.12.5
  - JJWT Jackson 0.12.5

---

## Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd hashing
```

### 2. Build the Project

```bash
mvn clean compile
```

### 3. Run the Demo

```bash
mvn exec:java -Dexec.mainClass="org.java.JwtHashingDemo"
```

Or use your IDE to run `JwtHashingDemo.java` directly.

---

## How It Works

### Workflow Overview

```
SERVER                          END USER                       SERVER
------                          --------                       ------
1. Generate key          →
2. Encode key (Hex/B64)  →      3. Receive encoded key    →
                         →      4. Decode key to bytes    →
                         →      5. Create SecretKey       →
                         →      6. Sign JWT with key      →
                         ←      7. Send JWT               ←   8. Verify JWT signature
                                                          ←   9. Extract claims
```

### Step-by-Step Process

#### Server Side - Key Generation

```java
// Generate a secure 256-bit key for HS256
SecretKey secretKey = Jwts.SIG.HS256.key().build();

// Encode the key for distribution
byte[] keyBytes = secretKey.getEncoded();
String hexKey = KeyEncodingUtils.encodeToHex(keyBytes);
String base64Key = KeyEncodingUtils.encodeToBase64(keyBytes);
String base64UrlKey = KeyEncodingUtils.encodeToBase64Url(keyBytes);
```

#### End User - Key Decoding and JWT Signing

```java
// Receive encoded key from server (e.g., Base64)
String receivedKey = "dGhpc2lzYXNhbXBsZWtleWZvcmRlbW9zdHJhdGlvbg==";

// Decode back to binary
byte[] keyBytes = KeyEncodingUtils.decodeFromBase64(receivedKey);

// Reconstruct the SecretKey
SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

// Create and sign a JWT
String jwt = JwtUtils.createAndSignJwt(
    "user123",                               // subject
    "jwt-demo",                             // issuer
    new Date(),                             // issuedAt
    new Date(System.currentTimeMillis() + 3600000), // expiration
    secretKey                               // signing key
);
```

#### Server Side - JWT Verification

```java
// Verify the JWT with the original secret key
Claims claims = JwtUtils.verifyAndGetClaims(jwt, secretKey);

if (claims != null) {
    System.out.println("Subject: " + claims.getSubject());
    System.out.println("Issuer: " + claims.getIssuer());
    System.out.println("Verification: SUCCESS");
}
```

---

## Key Concepts

### 1. JWT (JSON Web Token)

A JWT consists of three parts separated by dots (`.`):

```
header.payload.signature
```

- **Header**: Metadata about the token (algorithm, type)
- **Payload**: Claims (data) about the user or entity
- **Signature**: HMAC hash to ensure integrity and authenticity

### 2. HMAC Algorithms

**HMAC** (Hash-based Message Authentication Code) uses a secret key and a hash function to create a signature.

#### HS256 (HMAC-SHA256)
- **Key Size**: Minimum 256 bits (32 bytes)
- **Hash Function**: SHA-256
- **Output**: 256-bit signature
- **Use Case**: Standard security for most applications

#### HS512 (HMAC-SHA512)
- **Key Size**: Minimum 512 bits (64 bytes)
- **Hash Function**: SHA-512
- **Output**: 512-bit signature
- **Use Case**: Higher security for sensitive applications

### 3. Key Encoding Formats

#### Hex (Hexadecimal)
- **Format**: `0-9a-f` characters
- **Length**: 64 chars (HS256), 128 chars (HS512)
- **Pros**: Human-readable, easy to debug
- **Cons**: Longest representation, case-sensitive
- **Example**: `3a7bd3e2f1c4d5e6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0`

#### Base64
- **Format**: `A-Za-z0-9+/` with `=` padding
- **Length**: 44 chars (HS256), 88 chars (HS512)
- **Pros**: Compact, widely supported
- **Cons**: Contains `+` and `/` (not URL-safe)
- **Example**: `O3vT4vHE1eanuMnQ4fKjtMXW5/ipsA0t4/SltsfY6fA=`

#### Base64URL
- **Format**: `A-Za-z0-9-_` (no padding)
- **Length**: 43 chars (HS256), 86 chars (HS512)
- **Pros**: URL-safe, no padding, most compact
- **Cons**: Less common in legacy systems
- **Example**: `O3vT4vHE1eanuMnQ4fKjtMXW5_ipsA0t4_SltsfY6fA`
- **Recommended**: Best for modern APIs

### 4. Why Different Encodings?

All three encodings represent the same binary data:

```
Binary:    [0x3a, 0x7b, 0xd3, 0xe2, ...]
   ↓
Hex:       3a7bd3e2f1c4d5e6...
Base64:    O3vT4vHE1eanuMnQ...=
Base64URL: O3vT4vHE1eanuMnQ..._
```

Choose based on your use case:
- **Hex**: Debugging, human inspection
- **Base64**: Traditional APIs, email
- **Base64URL**: REST APIs, query parameters, modern web apps

---

## Security Considerations

### Best Practices

1. **Key Generation**
   - ✅ Use cryptographically secure random generation (JJWT handles this)
   - ✅ Use minimum key sizes: 256 bits for HS256, 512 bits for HS512
   - ❌ Never use predictable keys or weak passwords

2. **Key Storage**
   - ✅ Store keys securely (environment variables, vaults, HSMs)
   - ✅ Never commit keys to version control
   - ✅ Rotate keys periodically
   - ❌ Never hardcode keys in source code

3. **Key Distribution**
   - ✅ Use secure channels (HTTPS, TLS)
   - ✅ Consider using asymmetric cryptography (RS256) for public distribution
   - ❌ Never send keys over unencrypted channels

4. **JWT Usage**
   - ✅ Always verify signatures before trusting claims
   - ✅ Check expiration timestamps
   - ✅ Use short expiration times (minutes to hours, not days)
   - ❌ Never trust JWT content without verification

5. **Algorithm Selection**
   - ✅ Use HS256 for standard security
   - ✅ Use HS512 for higher security requirements
   - ✅ Consider RS256/ES256 if you need public key distribution
   - ❌ Never use "none" algorithm in production

### Common Vulnerabilities to Avoid

- **Algorithm Confusion**: Always verify the algorithm matches expected (JJWT prevents this)
- **Key Exposure**: Never log or expose secret keys
- **Weak Keys**: Use full-length cryptographic keys (not passwords)
- **Missing Validation**: Always verify expiration, issuer, and audience claims

---

## Example Output

When you run the demo, you'll see output like this:

```
================================================================================
API Key Encoding Demo: JWT Signing with HS256 vs HS512
================================================================================

1. HS256 (HMAC-SHA256) - API Key Distribution Demonstration
================================================================================

SERVER SIDE: Generated Secret Key
--------------------------------------------------------------------------------
Key Size: 256 bits (32 bytes)
Algorithm: HS256 (HMAC-SHA256)

API Key Encoding Options (what to send to end users):

  1. Hex Encoding:
     3a7bd3e2f1c4d5e6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0
     Length: 64 characters

  2. Base64 Encoding:
     O3vT4vHE1eanuMnQ4fKjtMXW5/ipsA0t4/SltsfY6fA=
     Length: 44 characters

  3. Base64URL Encoding (URL-safe, no padding):
     O3vT4vHE1eanuMnQ4fKjtMXW5_ipsA0t4_SltsfY6fA
     Length: 43 characters

END USER FLOW - Hex Encoding:
--------------------------------------------------------------------------------
  Step 1: Received encoded key (Hex)
  Step 2: Decoded key to binary (32 bytes)
  Step 3: Creating and signing JWT...
  Generated JWT: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaXNzIjoi...
  Full JWT Length: 234 characters

SERVER SIDE - Verification:
  Subject: user123
  Issuer: jwt-demo
  Role: user
  Email: user@example.com
  JWT Verification: ✓ SUCCESS

[... similar output for Base64 and Base64URL encodings ...]

2. HS512 (HMAC-SHA512) - API Key Distribution Demonstration
================================================================================
[... similar output for HS512 with 512-bit keys ...]
```

---

## Use Cases

### When to Use This Pattern

1. **API Key Authentication**: Distribute secret keys to API consumers for request signing
2. **Microservices**: Share secret keys between internal services for JWT verification
3. **Mobile Apps**: Embed API keys securely in apps for server communication
4. **IoT Devices**: Use symmetric keys for resource-constrained devices
5. **Internal Tools**: Quick authentication for internal dashboards and tools

### When NOT to Use This Pattern

1. **Public APIs**: Consider asymmetric algorithms (RS256, ES256) where you can share public keys
2. **Third-Party Integration**: Use OAuth 2.0 or OpenID Connect instead
3. **Browser-Based Apps**: Can't securely store secret keys (use authorization code flow)
4. **High-Scale Systems**: Consider token-based auth with key rotation complexity

---

## Project Highlights

### Modern JJWT API Usage

This project uses JJWT 0.12.5's modern API:

```java
// Modern approach (used in this project)
SecretKey key = Jwts.SIG.HS256.key().build();

// Old approach (deprecated)
SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
```

### Clean Code Practices

- **Single Responsibility**: Each class has one clear purpose
- **Utility Classes**: Reusable utilities for encoding and JWT operations
- **Type Safety**: Uses `SecretKey` type instead of raw byte arrays
- **Error Handling**: Graceful error handling in verification
- **Documentation**: Comprehensive JavaDoc comments

---

## Further Enhancements

Potential improvements for production use:

1. **Configuration**: Move to external configuration (Spring Boot)
2. **Key Rotation**: Implement key versioning and rotation
3. **Claim Validation**: Add audience, issuer, and custom claim validation
4. **Error Handling**: More detailed error messages and logging
5. **Testing**: Add unit tests for all components
6. **Refresh Tokens**: Implement refresh token mechanism
7. **Revocation**: Add token revocation/blacklist support

---

## License

This is a demonstration project for educational purposes.

---

## Contributing

This is a learning/demo project. Feel free to fork and experiment!

---

## References

- [JJWT Documentation](https://github.com/jwtk/jjwt)
- [JWT.io](https://jwt.io/)
- [RFC 7519 - JSON Web Token](https://tools.ietf.org/html/rfc7519)
- [RFC 2104 - HMAC](https://tools.ietf.org/html/rfc2104)
- [OWASP JWT Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_for_Java_Cheat_Sheet.html)

---

**Author**: Demonstration Project
**Last Updated**: November 2025
**Java Version**: 21
**JJWT Version**: 0.12.5
