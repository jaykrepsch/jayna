package me.jayna.domain.form;

import java.sql.SQLException;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import me.jayna.domain.AbstractAuditingEntity;
import me.jayna.domain.GroupType;

@Entity
@Table(name = "custom_form_input")
public class CustomFormInput extends AbstractAuditingEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "config_id")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Config config;

  @Column
  @Lob
  private byte[] payload;

  @ManyToOne(fetch = FetchType.EAGER)
  private GroupType groupType;

  @Column(name = "comment", length = 2000)
  private String comment;

  @Column(name = "description", length = 2000)
  private String description;

  public CustomFormInput() {
  }

  public CustomFormInput(Config config, String payload) throws SerialException, SQLException {
    setConfig(config);
    setPayload(payload.getBytes());
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public byte[] getPayload() {
    return this.payload;
  }

  public void setPayload(byte[] payload) {
    this.payload = payload;
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

  public CustomFormInput id(Long id) {
    setId(id);
    return this;
  }

  public CustomFormInput config(Config config) {
    setConfig(config);
    return this;
  }

  public CustomFormInput payload(byte[] payload) {
    setPayload(payload);
    return this;
  }

}
