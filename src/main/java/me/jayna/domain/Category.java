package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "sequenceGenerator"
  )
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "translation_key")
  private String translationKey;

  @Column(name = "entity_name")
  private String entityName;

  @OneToMany(mappedBy = "category")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(
    value = { "subCategoryGroups", "category" },
    allowSetters = true
  )
  private Set<SubCategory> subCategories = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Category id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public Category name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreatedDate() {
    return this.createdDate;
  }

  public Category createdDate(LocalDate createdDate) {
    this.setCreatedDate(createdDate);
    return this;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public String getTranslationKey() {
    return this.translationKey;
  }

  public Category translationKey(String translationKey) {
    this.setTranslationKey(translationKey);
    return this;
  }

  public void setTranslationKey(String translationKey) {
    this.translationKey = translationKey;
  }

  public String getEntityName() {
    return this.entityName;
  }

  public Category entityName(String entityName) {
    this.setEntityName(entityName);
    return this;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public Set<SubCategory> getSubCategories() {
    return this.subCategories;
  }

  public void setSubCategories(Set<SubCategory> subCategories) {
    if (this.subCategories != null) {
      this.subCategories.forEach(i -> i.setCategory(null));
    }
    if (subCategories != null) {
      subCategories.forEach(i -> i.setCategory(this));
    }
    this.subCategories = subCategories;
  }

  public Category subCategories(Set<SubCategory> subCategories) {
    this.setSubCategories(subCategories);
    return this;
  }

  public Category addSubCategory(SubCategory subCategory) {
    this.subCategories.add(subCategory);
    subCategory.setCategory(this);
    return this;
  }

  public Category removeSubCategory(SubCategory subCategory) {
    this.subCategories.remove(subCategory);
    subCategory.setCategory(null);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Category)) {
      return false;
    }
    return id != null && id.equals(((Category) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", translationKey='" + getTranslationKey() + "'" +
            ", entityName='" + getEntityName() + "'" +
            "}";
    }
}
