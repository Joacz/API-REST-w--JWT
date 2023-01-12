package net.joago.restjwt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.joago.restjwt.model.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {

}
