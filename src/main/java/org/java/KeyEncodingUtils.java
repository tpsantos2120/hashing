package org.java;

import java.util.Base64;
import java.util.HexFormat;

/**
 * Utility class for encoding and decoding secret keys in different formats.
 * Supports Hex, Base64, and Base64URL encodings.
 */
public class KeyEncodingUtils {

  /**
   * Displays the same key bytes in three different encoding formats.
   *
   * @param keyBytes The binary key bytes to encode
   */
  public static void displayKeyEncodings(byte[] keyBytes) {
    String hexEncoded = encodeToHex(keyBytes);
    String base64Encoded = encodeToBase64(keyBytes);
    String base64UrlEncoded = encodeToBase64Url(keyBytes);

    System.out.println("API Key Encoding Options (what to send to end users):");
    System.out.println();
    System.out.println("  1. Hex Encoding:");
    System.out.println("     " + hexEncoded);
    System.out.println("     Length: " + hexEncoded.length() + " characters");
    System.out.println();
    System.out.println("  2. Base64 Encoding:");
    System.out.println("     " + base64Encoded);
    System.out.println("     Length: " + base64Encoded.length() + " characters");
    System.out.println();
    System.out.println("  3. Base64URL Encoding (URL-safe, no padding):");
    System.out.println("     " + base64UrlEncoded);
    System.out.println("     Length: " + base64UrlEncoded.length() + " characters");
    System.out.println();
  }

  /**
   * Encodes binary key bytes to hexadecimal format.
   *
   * @param keyBytes The binary key bytes
   * @return Hex-encoded string (lowercase)
   */
  public static String encodeToHex(byte[] keyBytes) {
    return HexFormat.of().formatHex(keyBytes);
  }

  /**
   * Encodes binary key bytes to Base64 format.
   *
   * @param keyBytes The binary key bytes
   * @return Base64-encoded string with padding
   */
  public static String encodeToBase64(byte[] keyBytes) {
    return Base64.getEncoder().encodeToString(keyBytes);
  }

  /**
   * Encodes binary key bytes to Base64URL format (URL-safe, no padding).
   *
   * @param keyBytes The binary key bytes
   * @return Base64URL-encoded string without padding
   */
  public static String encodeToBase64Url(byte[] keyBytes) {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
  }

  /**
   * Decodes a key from its string representation based on the encoding type.
   *
   * @param encodedKey   The encoded key string
   * @param encodingType The encoding type ("Hex", "Base64", or "Base64URL")
   * @return The decoded binary key bytes
   * @throws IllegalArgumentException if the encoding type is unknown
   */
  public static byte[] decodeKey(String encodedKey, String encodingType) {
    return switch (encodingType) {
      case "Hex" -> decodeFromHex(encodedKey);
      case "Base64" -> decodeFromBase64(encodedKey);
      case "Base64URL" -> decodeFromBase64Url(encodedKey);
      default -> throw new IllegalArgumentException("Unknown encoding type: " + encodingType);
    };
  }

  /**
   * Decodes a hex-encoded string to binary bytes.
   *
   * @param hexString The hex-encoded string
   * @return The decoded binary bytes
   */
  public static byte[] decodeFromHex(String hexString) {
    return HexFormat.of().parseHex(hexString);
  }

  /**
   * Decodes a Base64-encoded string to binary bytes.
   *
   * @param base64String The Base64-encoded string
   * @return The decoded binary bytes
   */
  public static byte[] decodeFromBase64(String base64String) {
    return Base64.getDecoder().decode(base64String);
  }

  /**
   * Decodes a Base64URL-encoded string to binary bytes.
   *
   * @param base64UrlString The Base64URL-encoded string
   * @return The decoded binary bytes
   */
  public static byte[] decodeFromBase64Url(String base64UrlString) {
    return Base64.getUrlDecoder().decode(base64UrlString);
  }
}
