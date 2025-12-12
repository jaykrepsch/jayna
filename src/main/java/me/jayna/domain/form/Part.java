package me.jayna.domain.form;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@Entity
@Table(name = "form_part")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Part implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @ManyToOne
  @JsonIgnore
  private Config config;

  @OneToMany(mappedBy = "part", fetch = FetchType.EAGER)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @JsonIgnoreProperties(value = { "part" }, allowSetters = true)
  private Set<Field> fields = new HashSet<>();

  public Part() {
  }

  public Part(Long id, String title, Config config, Set<Field> fields) {
    this.id = id;
    this.title = title;
    this.config = config;
    this.fields = fields;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public Set<Field> getFields() {
    return this.fields;
  }

  public void setFields(Set<Field> fields) {
    this.fields = fields;
  }

  public Part id(Long id) {
    setId(id);
    return this;
  }

  public Part title(String title) {
    setTitle(title);
    return this;
  }

  public Part config(Config config) {
    setConfig(config);
    return this;
  }

  public Part fields(Set<Field> fields) {
    setFields(fields);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Part)) {
      return false;
    }
    Part part = (Part) o;
    return Objects.equals(id, part.id) && Objects.equals(title, part.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", title='" + getTitle() + "'" +
        ", fields='" + getFields() + "'" +
        "}";
  }

}
