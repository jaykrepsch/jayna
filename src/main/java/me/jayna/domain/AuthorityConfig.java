package me.jayna.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthorityConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "allowed_contracts")
  private String allowedContracts;

  @Column(name = "allowed_real_estates")
  private String allowedRealEstates;

  @Column(name = "allowed_devices")
  private String allowedDevices;

  @Column(name = "allowed_objects")
  private String allowedObjects;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAllowedContracts() {
    return this.allowedContracts;
  }

  public void setAllowedContracts(String allowedContracts) {
    this.allowedContracts = allowedContracts;
  }

  public String getAllowedRealEstates() {
    return this.allowedRealEstates;
  }

  public void setAllowedRealEstates(String allowedRealEstates) {
    this.allowedRealEstates = allowedRealEstates;
  }

  public String getAllowedDevices() {
    return this.allowedDevices;
  }

  public void setAllowedDevices(String allowedDevices) {
    this.allowedDevices = allowedDevices;
  }

  public String getAllowedObjects() {
    return this.allowedObjects;
  }

  public void setAllowedObjects(String allowedObjects) {
    this.allowedObjects = allowedObjects;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AuthorityConfig)) {
      return false;
    }
    return Objects.equals(id, ((AuthorityConfig) o).id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "AuthorityConfig{" +
        "id='" + id + '\'' +
        "}";
  }
}
