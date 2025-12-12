package me.jayna.repository.form;

import me.jayna.domain.form.Field;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Field} entity.
 */
public interface FieldRepository extends JpaRepository<Field, Long> {
}
