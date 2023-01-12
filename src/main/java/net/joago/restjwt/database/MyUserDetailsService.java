package net.joago.restjwt.database;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;
import net.joago.restjwt.model.UserDTO;
import net.joago.restjwt.model.Users;
import net.joago.restjwt.repo.UserRepository;

@Slf4j
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<Users> userByUsername = usersRepository.findByUsername(username);

    if (!userByUsername.isPresent()) {
      log.error("Could not find user with that username: {}", username);
      throw new UsernameNotFoundException("Invalid credentials!");
    }
    Users user = userByUsername.get();
    if (user == null || !user.getUsername().equals(username)) {
      log.error("Could not find user with that username: {}", username);
      throw new UsernameNotFoundException("Invalid credentials!");
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    user.getRoles().stream()
        .forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority.getRole())));

    return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), true, true, true, true,
        grantedAuthorities);
  }

}
