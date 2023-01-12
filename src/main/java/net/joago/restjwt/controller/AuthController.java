package net.joago.restjwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.joago.restjwt.service.TokenService;

@RestController
public class AuthController {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(AuthController.class);
  @Autowired
  private TokenService tokenService;

  @PostMapping("/token")
  public String token(Authentication authentication) {
    LOG.debug("Token request for user: '{}'", authentication);
    String token = tokenService.generateToken(authentication);
    LOG.debug("Token: {}", token);
    return token;
  }
  
}
