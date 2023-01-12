package net.joago.restjwt.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends User {

  public UserDTO(Integer id, String username, String email, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(email, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.email = email;
    System.out.println("...userDTO created");

  }

  private String email;

  @Override
  public String toString() {
    return "UserDTO [email=" + email + "]";
  }

}
