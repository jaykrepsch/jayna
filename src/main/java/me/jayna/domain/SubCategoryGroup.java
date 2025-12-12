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
 * A SubCategoryGroup.
 */
@Entity
@Table(name = "sub_category_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubCategoryGroup implements Serializable {

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

  @OneToMany(mappedBy = "subCategoryGroup")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "subCategoryGroup" }, allowSetters = true)
  private Set<GroupType> groupTypes = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnoreProperties(value = { "subCategoryGroups" }, allowSetters = true)
  private SubCategory subCategory;

  @Enumerated(EnumType.STRING)
  @Column(name = "creation_type")
  private CreationType creationType;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public SubCategoryGroup id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public SubCategoryGroup name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreatedDate() {
    return this.createdDate;
  }

  public SubCategoryGroup createdDate(LocalDate createdDate) {
    this.setCreatedDate(createdDate);
    return this;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public SubCategoryGroup createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getTranslationKey() {
    return this.translationKey;
  }

  public SubCategoryGroup translationKey(String translationKey) {
    this.setTranslationKey(translationKey);
    return this;
  }

  public void setTranslationKey(String translationKey) {
    this.translationKey = translationKey;
  }

  public Set<GroupType> getGroupTypes() {
    return this.groupTypes;
  }

  public void setGroupTypes(Set<GroupType> groupTypes) {
    if (this.groupTypes != null) {
      this.groupTypes.forEach(i -> i.setSubCategoryGroup(null));
    }
    if (groupTypes != null) {
      groupTypes.forEach(i -> i.setSubCategoryGroup(this));
    }
    this.groupTypes = groupTypes;
  }

  public SubCategoryGroup groupTypes(Set<GroupType> groupTypes) {
    this.setGroupTypes(groupTypes);
    return this;
  }

  public SubCategoryGroup addGroupType(GroupType groupType) {
    this.groupTypes.add(groupType);
    groupType.setSubCategoryGroup(this);
    return this;
  }

  public SubCategoryGroup removeGroupType(GroupType groupType) {
    this.groupTypes.remove(groupType);
    groupType.setSubCategoryGroup(null);
    return this;
  }

  public SubCategory getSubCategory() {
    return this.subCategory;
  }

  public void setSubCategory(SubCategory subCategory) {
    this.subCategory = subCategory;
  }

  public SubCategoryGroup subCategory(SubCategory subCategory) {
    this.setSubCategory(subCategory);
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
    if (!(o instanceof SubCategoryGroup)) {
      return false;
    }
    return id != null && id.equals(((SubCategoryGroup) o).id);
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
    return "SubCategoryGroup{" +
        "id=" + getId() +
        ", name='" + getName() + "'" +
        ", createdDate='" + getCreatedDate() + "'" +
        ", translationKey='" + getTranslationKey() + "'" +
        "}";
  }
}
