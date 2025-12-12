package me.jayna.repository.form;

import me.jayna.domain.form.Part;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Part} entity.
 */
public interface PartRepository extends JpaRepository<Part, Long> {
}
