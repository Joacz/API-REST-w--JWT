package net.joago.restjwt.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class Users {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;
  private String username;
  private String email;
  private String password;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role"))
  private List<Roles> roles;

  @Builder.Default
  private Boolean accountNonExpired = true;
  @Builder.Default
  private Boolean accountNonLocked = true;
  @Builder.Default
  private Boolean credentialsNonExpired = true;
  @Builder.Default
  private Boolean enabled = true;

  @Override
  public String toString() {
    return "Users [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", roles="
        + roles + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
        + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + "]";
  }

}