package net.joago.restjwt.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private final JwtEncoder encoder;

  public TokenService(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    Set<String> authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(authentication.getName())
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .issuer(authentication.getName())
        .claim("roles",
            authorities)
        .build();

    String token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    System.out.println(token);
    return token;
  }

}
