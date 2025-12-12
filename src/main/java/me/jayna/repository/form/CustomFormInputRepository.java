package me.jayna.repository.form;

import me.jayna.domain.form.CustomFormInput;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the {@link CustomFormInput} entity.
 */
public interface CustomFormInputRepository extends JpaRepository<CustomFormInput, Long> {

  @Query(value = "select * from custom_form_input cfi " +
      "inner join form_config fc on fc.id = cfi.config_id " +
      "inner join group_type gt on gt.id = cfi.group_type_id " +
      "inner join sub_category_group scg on scg.id = gt.sub_category_group_id " +
      "inner join sub_category sc on sc.id = scg.sub_category_id " +
      "inner join category c on c.id = sc.category_id where c.id = ?1 and fc.application_user_id = ?2", nativeQuery = true)
  List<CustomFormInput> findAllByCategoryIdAndApplicationUserId(long categoryId, long applicationUserId);

  @Query(value = "select * from custom_form_input cfi " +
      "inner join form_config fc on fc.id = cfi.config_id " +
      "inner join group_type gt on gt.id = cfi.group_type_id " +
      "inner join sub_category_group scg on scg.id = gt.sub_category_group_id " +
      "inner join sub_category sc on sc.id = scg.sub_category_id " +
      "inner join category c on c.id = sc.category_id where fc.application_user_id = ?1", nativeQuery = true)
  List<CustomFormInput> findAllByApplicationUserId(long applicationUserId);
}
