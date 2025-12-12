package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import me.jayna.domain.enumeration.AddressType;
import me.jayna.domain.enumeration.CommunicationType;
import me.jayna.domain.enumeration.Gender;
import me.jayna.domain.enumeration.MaritalStatus;
import me.jayna.domain.enumeration.Rank;
import me.jayna.domain.enumeration.Salutation;
import me.jayna.domain.enumeration.Title;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "Contact.graph", attributeNodes = {
    @NamedAttributeNode(value = "groupType", subgraph = "groupType-subgraph"),
    @NamedAttributeNode(value = "contactRealestates", subgraph = "contactRealestates-subgraph"),
    @NamedAttributeNode(value = "contactFinanceAccounts", subgraph = "contactFinanceAccounts-subgraph"),
    @NamedAttributeNode(value = "contactContracts", subgraph = "contactContracts-subgraph"),
    @NamedAttributeNode(value = "contactMobilities", subgraph = "contactMobilities-subgraph"),
}, subgraphs = {
    @NamedSubgraph(name = "groupType-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-subgraph")
    }),
    @NamedSubgraph(name = "subCategoryGroup-subgraph", attributeNodes = {
        @NamedAttributeNode("subCategory")
    }),
    @NamedSubgraph(name = "contactRealestates-subgraph", attributeNodes = {
        @NamedAttributeNode("realestate")
    }),
    @NamedSubgraph(name = "contactFinanceAccounts-subgraph", attributeNodes = {
        @NamedAttributeNode("financeaccount")
    }),
    @NamedSubgraph(name = "contactContracts-subgraph", attributeNodes = {
        @NamedAttributeNode("contract")
    }),
    @NamedSubgraph(name = "contactMobilities-subgraph", attributeNodes = {
        @NamedAttributeNode("mobility")
    })
})
@NamedEntityGraph(name = "Contact.overview", attributeNodes = {
    @NamedAttributeNode(value = "groupType", subgraph = "groupType-overview-subgraph")
}, subgraphs = {
    @NamedSubgraph(name = "groupType-overview-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategoryGroup", subgraph = "subCategoryGroup-overview-subgraph")
    }),
    @NamedSubgraph(name = "subCategoryGroup-overview-subgraph", attributeNodes = {
        @NamedAttributeNode(value = "subCategory", subgraph = "subCategory-overview-subgraph")
    }),
    @NamedSubgraph(name = "subCategory-overview-subgraph", attributeNodes = {
        @NamedAttributeNode("category")
    })
})
public class Contact extends AbstractCategoryEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "parent")
  private Contact parent;

  @Enumerated(EnumType.STRING)
  @Column(name = "title")
  private Title title;

  @Enumerated(EnumType.STRING)
  @Column(name = "salutation")
  private Salutation salutation;

  @Enumerated(EnumType.STRING)
  @Column(name = "rank")
  private Rank rank;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "sur_name")
  private String surName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "other_first_names")
  private String otherFirstNames;

  @Column(name = "birth_name")
  private String birthName;

  @Enumerated(EnumType.STRING)
  @Column(name = "marital_status")
  private MaritalStatus maritalStatus;

  @Column(name = "nick_name")
  private String nickName;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "birth_city")
  private String birthCity;

  @Column(name = "birth_country")
  private String birthCountry;

  @Column(name = "nationality")
  private String nationality;

  @Enumerated(EnumType.STRING)
  @Column(name = "address_type")
  private AddressType addressType;

  @Column(name = "street")
  private String street;

  @Column(name = "street_number")
  private String streetNumber;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "city")
  private String city;

  @Column(name = "additional_address_field_1")
  private String additionalAddressField1;

  @Column(name = "additional_address_field_2")
  private String additionalAddressField2;

  @Enumerated(EnumType.STRING)
  @Column(name = "communication_type")
  private CommunicationType communicationType;

  @Column(name = "phone_country_code")
  private Integer phoneCountryCode;

  @Column(name = "phone_prefix")
  private Integer phonePrefix;

  @Column(name = "phone_number")
  private Integer phoneNumber;

  @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
  private Set<ContactFinanceAccount> contactFinanceAccounts = new HashSet<>();

  @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
  private Set<ContactContract> contactContracts = new HashSet<>();

  @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
  private Set<ContactMobility> contactMobilities = new HashSet<>();

  @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
  @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
  private Set<ContactRealestate> contactRealestates = new HashSet<>();

  // @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY)
  // @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
  // private Set<DocumentContact> contactDocuments = new HashSet<>();

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public Contact id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Title getTitle() {
    return this.title;
  }

  public Contact title(Title title) {
    this.setTitle(title);
    return this;
  }

  public void setTitle(Title title) {
    this.title = title;
  }

  public Salutation getSalutation() {
    return this.salutation;
  }

  public Contact salutation(Salutation salutation) {
    this.setSalutation(salutation);
    return this;
  }

  public void setSalutation(Salutation salutation) {
    this.salutation = salutation;
  }

  public Rank getRank() {
    return this.rank;
  }

  public Contact rank(Rank rank) {
    this.setRank(rank);
    return this;
  }

  public void setRank(Rank rank) {
    this.rank = rank;
  }

  public Gender getGender() {
    return this.gender;
  }

  public Contact gender(Gender gender) {
    this.setGender(gender);
    return this;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getSurName() {
    return this.surName;
  }

  public Contact surName(String surName) {
    this.setSurName(surName);
    return this;
  }

  public void setSurName(String surName) {
    this.surName = surName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public Contact firstName(String firstName) {
    this.setFirstName(firstName);
    return this;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getOtherFirstNames() {
    return this.otherFirstNames;
  }

  public Contact otherFirstNames(String otherFirstNames) {
    this.setOtherFirstNames(otherFirstNames);
    return this;
  }

  public void setOtherFirstNames(String otherFirstNames) {
    this.otherFirstNames = otherFirstNames;
  }

  public String getBirthName() {
    return this.birthName;
  }

  public Contact birthName(String birthName) {
    this.setBirthName(birthName);
    return this;
  }

  public void setBirthName(String birthName) {
    this.birthName = birthName;
  }

  public MaritalStatus getMaritalStatus() {
    return this.maritalStatus;
  }

  public Contact maritalStatus(MaritalStatus maritalStatus) {
    this.setMaritalStatus(maritalStatus);
    return this;
  }

  public void setMaritalStatus(MaritalStatus maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public String getNickName() {
    return this.nickName;
  }

  public Contact nickName(String nickName) {
    this.setNickName(nickName);
    return this;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public LocalDate getBirthDate() {
    return this.birthDate;
  }

  public Contact birthDate(LocalDate birthDate) {
    this.setBirthDate(birthDate);
    return this;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getBirthCity() {
    return this.birthCity;
  }

  public Contact birthCity(String birthCity) {
    this.setBirthCity(birthCity);
    return this;
  }

  public void setBirthCity(String birthCity) {
    this.birthCity = birthCity;
  }

  public String getBirthCountry() {
    return this.birthCountry;
  }

  public Contact birthCountry(String birthCountry) {
    this.setBirthCountry(birthCountry);
    return this;
  }

  public void setBirthCountry(String birthCountry) {
    this.birthCountry = birthCountry;
  }

  public String getNationality() {
    return this.nationality;
  }

  public Contact nationality(String nationality) {
    this.setNationality(nationality);
    return this;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public AddressType getAddressType() {
    return this.addressType;
  }

  public Contact addressType(AddressType addressType) {
    this.setAddressType(addressType);
    return this;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

  public String getStreet() {
    return this.street;
  }

  public Contact street(String street) {
    this.setStreet(street);
    return this;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getStreetNumber() {
    return this.streetNumber;
  }

  public Contact streetNumber(String streetNumber) {
    this.setStreetNumber(streetNumber);
    return this;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public Contact postalCode(String postalCode) {
    this.setPostalCode(postalCode);
    return this;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return this.city;
  }

  public Contact city(String city) {
    this.setCity(city);
    return this;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAdditionalAddressField1() {
    return this.additionalAddressField1;
  }

  public Contact additionalAddressField1(String additionalAddressField1) {
    this.setAdditionalAddressField1(additionalAddressField1);
    return this;
  }

  public void setAdditionalAddressField1(String additionalAddressField1) {
    this.additionalAddressField1 = additionalAddressField1;
  }

  public String getAdditionalAddressField2() {
    return this.additionalAddressField2;
  }

  public Contact additionalAddressField2(String additionalAddressField2) {
    this.setAdditionalAddressField2(additionalAddressField2);
    return this;
  }

  public void setAdditionalAddressField2(String additionalAddressField2) {
    this.additionalAddressField2 = additionalAddressField2;
  }

  public CommunicationType getCommunicationType() {
    return this.communicationType;
  }

  public Contact communicationType(CommunicationType communicationType) {
    this.setCommunicationType(communicationType);
    return this;
  }

  public void setCommunicationType(CommunicationType communicationType) {
    this.communicationType = communicationType;
  }

  public Integer getPhoneCountryCode() {
    return this.phoneCountryCode;
  }

  public Contact phoneCountryCode(Integer phoneCountryCode) {
    this.setPhoneCountryCode(phoneCountryCode);
    return this;
  }

  public void setPhoneCountryCode(Integer phoneCountryCode) {
    this.phoneCountryCode = phoneCountryCode;
  }

  public Integer getPhonePrefix() {
    return this.phonePrefix;
  }

  public Contact phonePrefix(Integer phonePrefix) {
    this.setPhonePrefix(phonePrefix);
    return this;
  }

  public void setPhonePrefix(Integer phonePrefix) {
    this.phonePrefix = phonePrefix;
  }

  public Integer getPhoneNumber() {
    return this.phoneNumber;
  }

  public Contact phoneNumber(Integer phoneNumber) {
    this.setPhoneNumber(phoneNumber);
    return this;
  }

  public void setPhoneNumber(Integer phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Contact groupType(GroupType groupType) {
    this.setGroupType(groupType);
    return this;
  }

  public Set<ContactFinanceAccount> getContactFinanceAccounts() {
    return this.contactFinanceAccounts;
  }

  public void setContactFinanceAccounts(
      Set<ContactFinanceAccount> contactFinanceAccounts) {
    if (this.contactFinanceAccounts != null) {
      this.contactFinanceAccounts.forEach(i -> i.setContact(null));
    }
    if (contactFinanceAccounts != null) {
      contactFinanceAccounts.forEach(i -> i.setContact(this));
    }
    this.contactFinanceAccounts = contactFinanceAccounts;
  }

  public Contact contactFinanceAccounts(
      Set<ContactFinanceAccount> contactFinanceAccounts) {
    this.setContactFinanceAccounts(contactFinanceAccounts);
    return this;
  }

  public Contact addContactFinanceAccount(
      ContactFinanceAccount contactFinanceAccount) {
    this.contactFinanceAccounts.add(contactFinanceAccount);
    contactFinanceAccount.setContact(this);
    return this;
  }

  public Contact removeContactFinanceAccount(
      ContactFinanceAccount contactFinanceAccount) {
    this.contactFinanceAccounts.remove(contactFinanceAccount);
    contactFinanceAccount.setContact(null);
    return this;
  }

  public Set<ContactContract> getContactContracts() {
    return this.contactContracts;
  }

  public void setContactContracts(Set<ContactContract> contactContracts) {
    this.contactContracts = contactContracts;
  }

  public Contact contactContracts(
      Set<ContactContract> contactContracts) {
    this.setContactContracts(contactContracts);
    return this;
  }

  public Contact addContactContract(
      ContactContract contactContract) {
    this.contactContracts.add(contactContract);
    contactContract.setContact(this);
    return this;
  }

  public Contact removeContactContract(
      ContactContract contactContract) {
    this.contactContracts.remove(contactContract);
    contactContract.setContact(null);
    return this;
  }

  public Set<ContactMobility> getContactMobilities() {
    return this.contactMobilities;
  }

  public void setContactMobilities(Set<ContactMobility> contactMobilities) {
    this.contactMobilities = contactMobilities;
  }

  public Contact contactMobilities(
      Set<ContactMobility> contactMobilities) {
    this.setContactMobilities(contactMobilities);
    return this;
  }

  public Contact addContactMobility(
      ContactMobility contactMobility) {
    this.contactMobilities.add(contactMobility);
    contactMobility.setContact(this);
    return this;
  }

  public Contact removeContactMobility(
      ContactMobility contactMobility) {
    this.contactMobilities.remove(contactMobility);
    contactMobility.setContact(null);
    return this;
  }

  public Set<ContactRealestate> getContactRealestates() {
    return this.contactRealestates;
  }

  public void setContactRealestates(Set<ContactRealestate> contactRealestates) {
    this.contactRealestates = contactRealestates;
  }

  public Contact contactRealestates(
      Set<ContactRealestate> contactRealestates) {
    this.setContactRealestates(contactRealestates);
    return this;
  }

  public Contact addContactRealestate(
      ContactRealestate contactRealestate) {
    this.contactRealestates.add(contactRealestate);
    contactRealestate.setContact(this);
    return this;
  }

  public Contact removeContactRealestate(
      ContactRealestate contactRealestate) {
    this.contactRealestates.remove(contactRealestate);
    contactRealestate.setContact(null);
    return this;
  }

  // public Set<DocumentContact> getContactDocuments() {
  //   return this.contactDocuments;
  // }

  // public void setContactDocuments(Set<DocumentContact> contactDocuments) {
  //   this.contactDocuments = contactDocuments;
  // }

  // public Contact contactDocuments(Set<DocumentContact> contactDocuments) {
  //   this.setContactDocuments(contactDocuments);
  //   return this;
  // }

  // public Contact addContactDocument(DocumentContact documentContact) {
  //   this.contactDocuments.add(documentContact);
  //   documentContact.setContact(this);
  //   return this;
  // }

  // public Contact removeContactDocument(DocumentContact documentContact) {
  //   this.contactDocuments.remove(documentContact);
  //   documentContact.setContact(null);
  //   return this;
  // }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Contact)) {
      return false;
    }
    return id != null && id.equals(((Contact) o).id);
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
    return "Contact{" +
        "id=" + getId() +
        ", title='" + getTitle() + "'" +
        ", salutation='" + getSalutation() + "'" +
        ", rank='" + getRank() + "'" +
        ", gender='" + getGender() + "'" +
        ", surName='" + getSurName() + "'" +
        ", firstName='" + getFirstName() + "'" +
        ", otherFirstNames='" + getOtherFirstNames() + "'" +
        ", birthName='" + getBirthName() + "'" +
        ", maritalStatus='" + getMaritalStatus() + "'" +
        ", nickName='" + getNickName() + "'" +
        ", birthDate='" + getBirthDate() + "'" +
        ", birthCity='" + getBirthCity() + "'" +
        ", birthCountry='" + getBirthCountry() + "'" +
        ", nationality='" + getNationality() + "'" +
        ", addressType='" + getAddressType() + "'" +
        ", street='" + getStreet() + "'" +
        ", streetNumber='" + getStreetNumber() + "'" +
        ", postalCode='" + getPostalCode() + "'" +
        ", city='" + getCity() + "'" +
        ", additionalAddressField1='" + getAdditionalAddressField1() + "'" +
        ", additionalAddressField2='" + getAdditionalAddressField2() + "'" +
        ", communicationType='" + getCommunicationType() + "'" +
        ", phoneCountryCode=" + getPhoneCountryCode() +
        ", phonePrefix=" + getPhonePrefix() +
        ", phoneNumber=" + getPhoneNumber() +
        ", comment='" + getComment() + "'" +
        "}";
  }
}
