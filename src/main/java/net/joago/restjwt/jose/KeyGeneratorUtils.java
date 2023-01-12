package net.joago.restjwt.jose;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtils {

  private KeyGeneratorUtils() {
  }

  public static KeyPair generateRsaKey() {
    KeyPair keyPair;

    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      keyPair = generator.genKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    System.out.println("...keys generated");

    return keyPair;
  }

}
