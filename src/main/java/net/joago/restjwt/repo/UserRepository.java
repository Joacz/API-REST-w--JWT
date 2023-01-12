package net.joago.restjwt.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import net.joago.restjwt.model.Roles;
import net.joago.restjwt.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

  @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "roles" })
  public Optional<Users> findByUsername(String username);

  @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "roles" })
  public List<Users> findAll();

  @Query(nativeQuery = true, name = "select r.role from roles r inner join users u inner join userroles ur on ur.role = r.role and u.username = ur.user WHERE username = ?")
  public List<Roles> findRolesByUsername(String username);

}
