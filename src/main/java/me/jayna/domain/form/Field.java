package me.jayna.domain.form;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

@Entity
@Table(name = "form_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Field implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JsonIgnore
  private Part part;

  @Column
  private String label;

  @Column
  private String name;

  @Column
  private String placeholder;

  @Column
  private String rules;

  @Column
  private String type;

  @Column
  private Boolean showInOverview = false;

  public Field() {
  }

  public Field(Long id, Part part, String label, String name, String placeholder, String rules, String type,
      Boolean showInOverview) {
    this.id = id;
    this.part = part;
    this.label = label;
    this.name = name;
    this.placeholder = placeholder;
    this.rules = rules;
    this.type = type;
    this.showInOverview = showInOverview;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Part getPart() {
    return this.part;
  }

  public void setPart(Part part) {
    this.part = part;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlaceholder() {
    return this.placeholder;
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }

  public String getRules() {
    return this.rules;
  }

  public void setRules(String rules) {
    this.rules = rules;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Field id(Long id) {
    setId(id);
    return this;
  }

  public Field part(Part part) {
    setPart(part);
    return this;
  }

  public Field label(String label) {
    setLabel(label);
    return this;
  }

  public Field name(String name) {
    setName(name);
    return this;
  }

  public Field placeholder(String placeholder) {
    setPlaceholder(placeholder);
    return this;
  }

  public Field rules(String rules) {
    setRules(rules);
    return this;
  }

  public Field type(String type) {
    setType(type);
    return this;
  }

  public Boolean isShowInOverview() {
    return this.showInOverview;
  }

  public Boolean getShowInOverview() {
    return this.showInOverview;
  }

  public void setShowInOverview(Boolean showInOverview) {
    this.showInOverview = showInOverview;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Field)) {
      return false;
    }
    Field field = (Field) o;
    return Objects.equals(id, field.id)
        && Objects.equals(label, field.label)
        && Objects.equals(name, field.name) && Objects.equals(placeholder, field.placeholder)
        && Objects.equals(rules, field.rules) && Objects.equals(type, field.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, name, placeholder, rules, type);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", label='" + getLabel() + "'" +
        ", name='" + getName() + "'" +
        ", placeholder='" + getPlaceholder() + "'" +
        ", rules='" + getRules() + "'" +
        ", type='" + getType() + "'" +
        "}";
  }

}
