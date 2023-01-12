package net.joago.restjwt.controller;

import org.springframework.web.bind.annotation.RestController;

import net.joago.restjwt.database.MyUserDetailsService;
import net.joago.restjwt.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {

  @Autowired
  UserRepository detailsService;

  @GetMapping("/")
  public String home(Authentication authentication) {

    return detailsService.findAll().toString();

  }

  @GetMapping("/user")
  public String user(Authentication authentication) {
    return "Welkom on the User page! - Username: " + authentication.getName() + " - " + authentication.getAuthorities();
  }

  @GetMapping("/admin")
  public String admin(Authentication authentication) {
    return "Welkom on the Admin page! - Username: " + authentication.getName() + " - "
        + authentication.getAuthorities();
  }

}
