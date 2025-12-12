package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.jayna.domain.enumeration.CreationType;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GroupType.
 */
@Entity
@Table(name = "group_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "GroupType.graph", attributeNodes = {
    @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-subgraph")
}, subgraphs = {
    @NamedSubgraph(name = "subCategoryGroup-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategory", subgraph = "subCategory-subgraph")
    }),
    @NamedSubgraph(name = "subCategory-subgraph", attributeNodes = {
        @NamedAttributeNode("category")
    })
})
public class GroupType implements Serializable {

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

  @Column(name = "entity_name")
  private String entityName;

  @Column(name = "form_name")
  private String formName;

  @Column(name = "abbreviation")
  private String abbreviation;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnoreProperties(value = { "groupTypes" }, allowSetters = true)
  private SubCategoryGroup subCategoryGroup;

  @Enumerated(EnumType.STRING)
  @Column(name = "creation_type")
  private CreationType creationType;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public GroupType id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public GroupType name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreatedDate() {
    return this.createdDate;
  }

  public GroupType createdDate(LocalDate createdDate) {
    this.setCreatedDate(createdDate);
    return this;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public GroupType createdBy(String createdBy) {
    this.setCreatedBy(createdBy);
    return this;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getTranslationKey() {
    return this.translationKey;
  }

  public GroupType translationKey(String translationKey) {
    this.setTranslationKey(translationKey);
    return this;
  }

  public void setTranslationKey(String translationKey) {
    this.translationKey = translationKey;
  }

  public String getEntityName() {
    return this.entityName;
  }

  public GroupType entityName(String entityName) {
    this.setEntityName(entityName);
    return this;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getFormName() {
    return this.formName;
  }

  public GroupType formName(String formName) {
    this.setFormName(formName);
    return this;
  }

  public void setFormName(String formName) {
    this.formName = formName;
  }

  public String getAbbreviation() {
    return this.abbreviation;
  }

  public GroupType abbreviation(String abbreviation) {
    this.setAbbreviation(abbreviation);
    return this;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public SubCategoryGroup getSubCategoryGroup() {
    return this.subCategoryGroup;
  }

  public void setSubCategoryGroup(SubCategoryGroup subCategoryGroup) {
    this.subCategoryGroup = subCategoryGroup;
  }

  public GroupType subCategoryGroup(SubCategoryGroup subCategoryGroup) {
    this.setSubCategoryGroup(subCategoryGroup);
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
    if (!(o instanceof GroupType)) {
      return false;
    }
    return id != null && id.equals(((GroupType) o).id);
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
    return "GroupType{" +
        "id=" + getId() +
        ", name='" + getName() + "'" +
        ", createdDate='" + getCreatedDate() + "'" +
        ", translationKey='" + getTranslationKey() + "'" +
        ", entityName='" + getEntityName() + "'" +
        ", formName='" + getFormName() + "'" +
        ", abbreviation='" + getAbbreviation() + "'" +
        "}";
  }
}
