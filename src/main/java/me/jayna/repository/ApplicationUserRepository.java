package me.jayna.repository;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationUser entity.
 */
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
  
  Optional<ApplicationUser> findOneByInternalUser(User user);
}
