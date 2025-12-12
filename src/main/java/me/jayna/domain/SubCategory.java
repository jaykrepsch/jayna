package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.jayna.domain.enumeration.CreationType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubCategory.
 */
@Entity
@Table(name = "sub_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubCategory implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "created_date")
  private LocalDate createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "translation_key")
  private String translationKey;

  @OneToMany(mappedBy = "subCategory")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "groupTypes", "subCategory" }, allowSetters = true)
  private Set<SubCategoryGroup> subCategoryGroups = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnoreProperties(value = { "subCategories" }, allowSetters = true)
  private Category category;

  @Enumerated(EnumType.STRING)
  @Column(name = "creation_type")
  private CreationType creationType;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public SubCategory id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public SubCategory name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreatedDate() {
    return this.createdDate;
  }

  public SubCategory createdDate(LocalDate createdDate) {
    this.setCreatedDate(createdDate);
    return this;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public SubCategory createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getTranslationKey() {
    return this.translationKey;
  }

  public SubCategory translationKey(String translationKey) {
    this.setTranslationKey(translationKey);
    return this;
  }

  public void setTranslationKey(String translationKey) {
    this.translationKey = translationKey;
  }

  public Set<SubCategoryGroup> getSubCategoryGroups() {
    return this.subCategoryGroups;
  }

  public void setSubCategoryGroups(Set<SubCategoryGroup> subCategoryGroups) {
    if (this.subCategoryGroups != null) {
      this.subCategoryGroups.forEach(i -> i.setSubCategory(null));
    }
    if (subCategoryGroups != null) {
      subCategoryGroups.forEach(i -> i.setSubCategory(this));
    }
    this.subCategoryGroups = subCategoryGroups;
  }

  public SubCategory subCategoryGroups(
      Set<SubCategoryGroup> subCategoryGroups) {
    this.setSubCategoryGroups(subCategoryGroups);
    return this;
  }

  public SubCategory addSubCategoryGroup(SubCategoryGroup subCategoryGroup) {
    this.subCategoryGroups.add(subCategoryGroup);
    subCategoryGroup.setSubCategory(this);
    return this;
  }

  public SubCategory removeSubCategoryGroup(SubCategoryGroup subCategoryGroup) {
    this.subCategoryGroups.remove(subCategoryGroup);
    subCategoryGroup.setSubCategory(null);
    return this;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public SubCategory category(Category category) {
    this.setCategory(category);
    return this;
  }

  public CreationType getCreationType() {
    return this.creationType;
  }

  public void setCreationType(CreationType creationType) {
    this.creationType = creationType;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SubCategory)) {
      return false;
    }
    return id != null && id.equals(((SubCategory) o).id);
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "SubCategory{" +
        "id=" + getId() +
        ", name='" + getName() + "'" +
        ", createdDate='" + getCreatedDate() + "'" +
        ", translationKey='" + getTranslationKey() + "'" +
        "}";
  }
}
