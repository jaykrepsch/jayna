package me.jayna.domain.form;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.GroupType;
import java.util.Objects;

@Entity
@Table(name = "form_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Config implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @OneToOne(fetch = FetchType.EAGER)
  private GroupType groupType;

  @OneToMany(mappedBy = "config", fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "config" }, allowSetters = true)
  private Set<Part> parts = new HashSet<>();

  @ManyToOne
  @JsonIgnore
  private ApplicationUser applicationUser;

  public Config() {
  }

  public Config(Long id, GroupType groupType, Set<Part> parts) {
    this.id = id;
    this.groupType = groupType;
    this.parts = parts;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GroupType getGroupType() {
    return this.groupType;
  }

  public void setGroupType(GroupType groupType) {
    this.groupType = groupType;
  }

  public Set<Part> getParts() {
    return this.parts;
  }

  public void setParts(Set<Part> parts) {
    this.parts = parts;
  }

  public Config id(Long id) {
    setId(id);
    return this;
  }

  public Config groupType(GroupType groupType) {
    setGroupType(groupType);
    return this;
  }

  public Config parts(Set<Part> parts) {
    setParts(parts);
    return this;
  }

  public ApplicationUser getApplicationUser() {
    return this.applicationUser;
  }

  public void setApplicationUser(ApplicationUser applicationUser) {
    this.applicationUser = applicationUser;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Config)) {
      return false;
    }
    Config config = (Config) o;
    return Objects.equals(id, config.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, groupType.getId());
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", parts='" + getParts() + "'" +
        "}";
  }
}
