package me.jayna.repository.form;

import me.jayna.domain.form.Config;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import me.jayna.domain.GroupType;
import me.jayna.domain.ApplicationUser;

/**
 * Spring Data JPA repository for the {@link Config} entity.
 */
public interface ConfigRepository extends JpaRepository<Config, Long> {

  Optional<Config> findOneByGroupType(GroupType groupType);

  List<Config> findByApplicationUser(ApplicationUser applicationUser);

  Optional<Config> findOneByIdAndApplicationUser(long id, ApplicationUser applicationUser);
}
