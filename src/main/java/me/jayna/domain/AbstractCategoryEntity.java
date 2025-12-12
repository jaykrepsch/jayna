package me.jayna.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.jayna.domain.enumeration.EntityState;

@MappedSuperclass
public class AbstractCategoryEntity extends AbstractAuditingEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
  private ApplicationUser applicationUser;

  @ManyToOne(fetch = FetchType.EAGER)
  private GroupType groupType;

  @Column(name = "label")
  private String label;

  @Enumerated(EnumType.STRING)
  @Column(name = "entity_state")
  private EntityState entityState;

  @Column(name = "comment", length = 2000)
  private String comment;

  @Column(name = "description", length = 2000)
  private String description;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ApplicationUser getApplicationUser() {
    return this.applicationUser;
  }

  public void setApplicationUser(ApplicationUser applicationUser) {
    this.applicationUser = applicationUser;
  }

  public GroupType getGroupType() {
    return this.groupType;
  }

  public void setGroupType(GroupType groupType) {
    this.groupType = groupType;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public EntityState getEntityState() {
    return this.entityState;
  }

  public void setEntityState(EntityState entityState) {
    this.entityState = entityState;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
