package net.joago.restjwt.security;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import net.joago.restjwt.database.MyUserDetailsService;
import net.joago.restjwt.jose.Jwks;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private RSAKey rsaKey;

  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService myUserDetailsService() {
    return new MyUserDetailsService();
  }

  @Bean
  public JwtAuthenticationConverter authenticationConverter() {
    final JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
    converter.setAuthoritiesClaimName("roles");
    converter.setAuthorityPrefix("");

    final JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
    authConverter.setJwtGrantedAuthoritiesConverter(converter);

    System.out.println("...authentication converter configured");

    return authConverter;
  }

  /*
   * @Bean
   * public UserDetailsManager users(DataSource dataSource) {
   * UserDetails admin = User
   * .withUsername("admin")
   * .password(encoder().encode("admin"))
   * .roles("USER", "ADMIN")
   * .build();
   * JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
   * users.setCreateAuthoritySql("insert into userroles(user, role) (?,?)");
   * users.createUser(admin);
   * return users;
   * }
   */

  @Order(Ordered.HIGHEST_PRECEDENCE)
  @Bean
  SecurityFilterChain tokenSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .securityMatcher("/token")
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/token").permitAll())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .csrf(csrf -> csrf.disable())
        .httpBasic(withDefaults())
        .build();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http 
        .authorizeHttpRequests(
            auth -> auth
                .requestMatchers("/user").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    System.out.println("...source configured");

    rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
    System.out.println("...encoder configured");

    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public JwtDecoder jwtDecoder() throws JOSEException {
    System.out.println("...decoder configured");

    return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
  }

  @Bean
  ApplicationListener<AuthenticationSuccessEvent> successEvent() {
    return event -> {
      System.out.println("Success Login " + event.getAuthentication().getClass().getSimpleName() + " - "
          + event.getAuthentication().getName() + " - " + event.getAuthentication().getAuthorities());
    };
  }

  @Bean
  ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
    return event -> {
      System.err.println("Bad Credentials Login " + event.getAuthentication().getClass().getSimpleName() + " - "
          + event.getAuthentication().getName());
    };
  }

}
