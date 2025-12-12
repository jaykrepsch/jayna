package me.jayna.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import me.jayna.domain.enumeration.AddressType;
import me.jayna.domain.enumeration.CommunicationType;
import me.jayna.domain.enumeration.Gender;
import me.jayna.domain.enumeration.MaritalStatus;
import me.jayna.domain.enumeration.Rank;
import me.jayna.domain.enumeration.Salutation;
import me.jayna.domain.enumeration.Title;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link me.jayna.domain.Contact} entity. This class is used
 * in {@link me.jayna.web.rest.ContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ContactCriteria implements Serializable, Criteria {

  /**
   * Class for filtering Title
   */
  public static class TitleFilter extends Filter<Title> {

    public TitleFilter() {}

    public TitleFilter(TitleFilter filter) {
      super(filter);
    }

    @Override
    public TitleFilter copy() {
      return new TitleFilter(this);
    }
  }

  /**
   * Class for filtering Salutation
   */
  public static class SalutationFilter extends Filter<Salutation> {

    public SalutationFilter() {}

    public SalutationFilter(SalutationFilter filter) {
      super(filter);
    }

    @Override
    public SalutationFilter copy() {
      return new SalutationFilter(this);
    }
  }

  /**
   * Class for filtering Rank
   */
  public static class RankFilter extends Filter<Rank> {

    public RankFilter() {}

    public RankFilter(RankFilter filter) {
      super(filter);
    }

    @Override
    public RankFilter copy() {
      return new RankFilter(this);
    }
  }

  /**
   * Class for filtering Gender
   */
  public static class GenderFilter extends Filter<Gender> {

    public GenderFilter() {}

    public GenderFilter(GenderFilter filter) {
      super(filter);
    }

    @Override
    public GenderFilter copy() {
      return new GenderFilter(this);
    }
  }

  /**
   * Class for filtering MaritalStatus
   */
  public static class MaritalStatusFilter extends Filter<MaritalStatus> {

    public MaritalStatusFilter() {}

    public MaritalStatusFilter(MaritalStatusFilter filter) {
      super(filter);
    }

    @Override
    public MaritalStatusFilter copy() {
      return new MaritalStatusFilter(this);
    }
  }

  /**
   * Class for filtering AddressType
   */
  public static class AddressTypeFilter extends Filter<AddressType> {

    public AddressTypeFilter() {}

    public AddressTypeFilter(AddressTypeFilter filter) {
      super(filter);
    }

    @Override
    public AddressTypeFilter copy() {
      return new AddressTypeFilter(this);
    }
  }

  /**
   * Class for filtering CommunicationType
   */
  public static class CommunicationTypeFilter
    extends Filter<CommunicationType> {

    public CommunicationTypeFilter() {}

    public CommunicationTypeFilter(CommunicationTypeFilter filter) {
      super(filter);
    }

    @Override
    public CommunicationTypeFilter copy() {
      return new CommunicationTypeFilter(this);
    }
  }

  private static final long serialVersionUID = 1L;

  private LongFilter id;

  private TitleFilter title;

  private SalutationFilter salutation;

  private RankFilter rank;

  private GenderFilter gender;

  private StringFilter surName;

  private StringFilter firstName;

  private StringFilter otherFirstNames;

  private StringFilter birthName;

  private MaritalStatusFilter maritalStatus;

  private StringFilter nickName;

  private LocalDateFilter birthDate;

  private StringFilter birthCity;

  private StringFilter birthCountry;

  private StringFilter nationality;

  private AddressTypeFilter addressType;

  private StringFilter street;

  private StringFilter streetNumber;

  private StringFilter postalCode;

  private StringFilter city;

  private StringFilter additionalAddressField1;

  private StringFilter additionalAddressField2;

  private CommunicationTypeFilter communicationType;

  private IntegerFilter phoneCountryCode;

  private IntegerFilter phonePrefix;

  private IntegerFilter phoneNumber;

  private StringFilter comment;

  private LongFilter applicationUserId;

  private LongFilter groupTypeId;

  private LongFilter contactFinanceAccountId;

  private Boolean distinct;

  public ContactCriteria() {}

  public ContactCriteria(ContactCriteria other) {
    this.id = other.id == null ? null : other.id.copy();
    this.title = other.title == null ? null : other.title.copy();
    this.salutation = other.salutation == null ? null : other.salutation.copy();
    this.rank = other.rank == null ? null : other.rank.copy();
    this.gender = other.gender == null ? null : other.gender.copy();
    this.surName = other.surName == null ? null : other.surName.copy();
    this.firstName = other.firstName == null ? null : other.firstName.copy();
    this.otherFirstNames =
      other.otherFirstNames == null ? null : other.otherFirstNames.copy();
    this.birthName = other.birthName == null ? null : other.birthName.copy();
    this.maritalStatus =
      other.maritalStatus == null ? null : other.maritalStatus.copy();
    this.nickName = other.nickName == null ? null : other.nickName.copy();
    this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
    this.birthCity = other.birthCity == null ? null : other.birthCity.copy();
    this.birthCountry =
      other.birthCountry == null ? null : other.birthCountry.copy();
    this.nationality =
      other.nationality == null ? null : other.nationality.copy();
    this.addressType =
      other.addressType == null ? null : other.addressType.copy();
    this.street = other.street == null ? null : other.street.copy();
    this.streetNumber =
      other.streetNumber == null ? null : other.streetNumber.copy();
    this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
    this.city = other.city == null ? null : other.city.copy();
    this.additionalAddressField1 =
      other.additionalAddressField1 == null
        ? null
        : other.additionalAddressField1.copy();
    this.additionalAddressField2 =
      other.additionalAddressField2 == null
        ? null
        : other.additionalAddressField2.copy();
    this.communicationType =
      other.communicationType == null ? null : other.communicationType.copy();
    this.phoneCountryCode =
      other.phoneCountryCode == null ? null : other.phoneCountryCode.copy();
    this.phonePrefix =
      other.phonePrefix == null ? null : other.phonePrefix.copy();
    this.phoneNumber =
      other.phoneNumber == null ? null : other.phoneNumber.copy();
    this.comment = other.comment == null ? null : other.comment.copy();
    this.applicationUserId =
      other.applicationUserId == null ? null : other.applicationUserId.copy();
    this.groupTypeId =
      other.groupTypeId == null ? null : other.groupTypeId.copy();
    this.contactFinanceAccountId =
      other.contactFinanceAccountId == null
        ? null
        : other.contactFinanceAccountId.copy();
    this.distinct = other.distinct;
  }

  @Override
  public ContactCriteria copy() {
    return new ContactCriteria(this);
  }

  public LongFilter getId() {
    return id;
  }

  public LongFilter id() {
    if (id == null) {
      id = new LongFilter();
    }
    return id;
  }

  public void setId(LongFilter id) {
    this.id = id;
  }

  public TitleFilter getTitle() {
    return title;
  }

  public TitleFilter title() {
    if (title == null) {
      title = new TitleFilter();
    }
    return title;
  }

  public void setTitle(TitleFilter title) {
    this.title = title;
  }

  public SalutationFilter getSalutation() {
    return salutation;
  }

  public SalutationFilter salutation() {
    if (salutation == null) {
      salutation = new SalutationFilter();
    }
    return salutation;
  }

  public void setSalutation(SalutationFilter salutation) {
    this.salutation = salutation;
  }

  public RankFilter getRank() {
    return rank;
  }

  public RankFilter rank() {
    if (rank == null) {
      rank = new RankFilter();
    }
    return rank;
  }

  public void setRank(RankFilter rank) {
    this.rank = rank;
  }

  public GenderFilter getGender() {
    return gender;
  }

  public GenderFilter gender() {
    if (gender == null) {
      gender = new GenderFilter();
    }
    return gender;
  }

  public void setGender(GenderFilter gender) {
    this.gender = gender;
  }

  public StringFilter getSurName() {
    return surName;
  }

  public StringFilter surName() {
    if (surName == null) {
      surName = new StringFilter();
    }
    return surName;
  }

  public void setSurName(StringFilter surName) {
    this.surName = surName;
  }

  public StringFilter getFirstName() {
    return firstName;
  }

  public StringFilter firstName() {
    if (firstName == null) {
      firstName = new StringFilter();
    }
    return firstName;
  }

  public void setFirstName(StringFilter firstName) {
    this.firstName = firstName;
  }

  public StringFilter getOtherFirstNames() {
    return otherFirstNames;
  }

  public StringFilter otherFirstNames() {
    if (otherFirstNames == null) {
      otherFirstNames = new StringFilter();
    }
    return otherFirstNames;
  }

  public void setOtherFirstNames(StringFilter otherFirstNames) {
    this.otherFirstNames = otherFirstNames;
  }

  public StringFilter getBirthName() {
    return birthName;
  }

  public StringFilter birthName() {
    if (birthName == null) {
      birthName = new StringFilter();
    }
    return birthName;
  }

  public void setBirthName(StringFilter birthName) {
    this.birthName = birthName;
  }

  public MaritalStatusFilter getMaritalStatus() {
    return maritalStatus;
  }

  public MaritalStatusFilter maritalStatus() {
    if (maritalStatus == null) {
      maritalStatus = new MaritalStatusFilter();
    }
    return maritalStatus;
  }

  public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public StringFilter getNickName() {
    return nickName;
  }

  public StringFilter nickName() {
    if (nickName == null) {
      nickName = new StringFilter();
    }
    return nickName;
  }

  public void setNickName(StringFilter nickName) {
    this.nickName = nickName;
  }

  public LocalDateFilter getBirthDate() {
    return birthDate;
  }

  public LocalDateFilter birthDate() {
    if (birthDate == null) {
      birthDate = new LocalDateFilter();
    }
    return birthDate;
  }

  public void setBirthDate(LocalDateFilter birthDate) {
    this.birthDate = birthDate;
  }

  public StringFilter getBirthCity() {
    return birthCity;
  }

  public StringFilter birthCity() {
    if (birthCity == null) {
      birthCity = new StringFilter();
    }
    return birthCity;
  }

  public void setBirthCity(StringFilter birthCity) {
    this.birthCity = birthCity;
  }

  public StringFilter getBirthCountry() {
    return birthCountry;
  }

  public StringFilter birthCountry() {
    if (birthCountry == null) {
      birthCountry = new StringFilter();
    }
    return birthCountry;
  }

  public void setBirthCountry(StringFilter birthCountry) {
    this.birthCountry = birthCountry;
  }

  public StringFilter getNationality() {
    return nationality;
  }

  public StringFilter nationality() {
    if (nationality == null) {
      nationality = new StringFilter();
    }
    return nationality;
  }

  public void setNationality(StringFilter nationality) {
    this.nationality = nationality;
  }

  public AddressTypeFilter getAddressType() {
    return addressType;
  }

  public AddressTypeFilter addressType() {
    if (addressType == null) {
      addressType = new AddressTypeFilter();
    }
    return addressType;
  }

  public void setAddressType(AddressTypeFilter addressType) {
    this.addressType = addressType;
  }

  public StringFilter getStreet() {
    return street;
  }

  public StringFilter street() {
    if (street == null) {
      street = new StringFilter();
    }
    return street;
  }

  public void setStreet(StringFilter street) {
    this.street = street;
  }

  public StringFilter getStreetNumber() {
    return streetNumber;
  }

  public StringFilter streetNumber() {
    if (streetNumber == null) {
      streetNumber = new StringFilter();
    }
    return streetNumber;
  }

  public void setStreetNumber(StringFilter streetNumber) {
    this.streetNumber = streetNumber;
  }

  public StringFilter getPostalCode() {
    return postalCode;
  }

  public StringFilter postalCode() {
    if (postalCode == null) {
      postalCode = new StringFilter();
    }
    return postalCode;
  }

  public void setPostalCode(StringFilter postalCode) {
    this.postalCode = postalCode;
  }

  public StringFilter getCity() {
    return city;
  }

  public StringFilter city() {
    if (city == null) {
      city = new StringFilter();
    }
    return city;
  }

  public void setCity(StringFilter city) {
    this.city = city;
  }

  public StringFilter getAdditionalAddressField1() {
    return additionalAddressField1;
  }

  public StringFilter additionalAddressField1() {
    if (additionalAddressField1 == null) {
      additionalAddressField1 = new StringFilter();
    }
    return additionalAddressField1;
  }

  public void setAdditionalAddressField1(StringFilter additionalAddressField1) {
    this.additionalAddressField1 = additionalAddressField1;
  }

  public StringFilter getAdditionalAddressField2() {
    return additionalAddressField2;
  }

  public StringFilter additionalAddressField2() {
    if (additionalAddressField2 == null) {
      additionalAddressField2 = new StringFilter();
    }
    return additionalAddressField2;
  }

  public void setAdditionalAddressField2(StringFilter additionalAddressField2) {
    this.additionalAddressField2 = additionalAddressField2;
  }

  public CommunicationTypeFilter getCommunicationType() {
    return communicationType;
  }

  public CommunicationTypeFilter communicationType() {
    if (communicationType == null) {
      communicationType = new CommunicationTypeFilter();
    }
    return communicationType;
  }

  public void setCommunicationType(CommunicationTypeFilter communicationType) {
    this.communicationType = communicationType;
  }

  public IntegerFilter getPhoneCountryCode() {
    return phoneCountryCode;
  }

  public IntegerFilter phoneCountryCode() {
    if (phoneCountryCode == null) {
      phoneCountryCode = new IntegerFilter();
    }
    return phoneCountryCode;
  }

  public void setPhoneCountryCode(IntegerFilter phoneCountryCode) {
    this.phoneCountryCode = phoneCountryCode;
  }

  public IntegerFilter getPhonePrefix() {
    return phonePrefix;
  }

  public IntegerFilter phonePrefix() {
    if (phonePrefix == null) {
      phonePrefix = new IntegerFilter();
    }
    return phonePrefix;
  }

  public void setPhonePrefix(IntegerFilter phonePrefix) {
    this.phonePrefix = phonePrefix;
  }

  public IntegerFilter getPhoneNumber() {
    return phoneNumber;
  }

  public IntegerFilter phoneNumber() {
    if (phoneNumber == null) {
      phoneNumber = new IntegerFilter();
    }
    return phoneNumber;
  }

  public void setPhoneNumber(IntegerFilter phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public StringFilter getComment() {
    return comment;
  }

  public StringFilter comment() {
    if (comment == null) {
      comment = new StringFilter();
    }
    return comment;
  }

  public void setComment(StringFilter comment) {
    this.comment = comment;
  }

  public LongFilter getApplicationUserId() {
    return applicationUserId;
  }

  public LongFilter applicationUserId() {
    if (applicationUserId == null) {
      applicationUserId = new LongFilter();
    }
    return applicationUserId;
  }

  public void setApplicationUserId(LongFilter applicationUserId) {
    this.applicationUserId = applicationUserId;
  }

  public LongFilter getGroupTypeId() {
    return groupTypeId;
  }

  public LongFilter groupTypeId() {
    if (groupTypeId == null) {
      groupTypeId = new LongFilter();
    }
    return groupTypeId;
  }

  public void setGroupTypeId(LongFilter groupTypeId) {
    this.groupTypeId = groupTypeId;
  }

  public LongFilter getContactFinanceAccountId() {
    return contactFinanceAccountId;
  }

  public LongFilter contactFinanceAccountId() {
    if (contactFinanceAccountId == null) {
      contactFinanceAccountId = new LongFilter();
    }
    return contactFinanceAccountId;
  }

  public void setContactFinanceAccountId(LongFilter contactFinanceAccountId) {
    this.contactFinanceAccountId = contactFinanceAccountId;
  }

  public Boolean getDistinct() {
    return distinct;
  }

  public void setDistinct(Boolean distinct) {
    this.distinct = distinct;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ContactCriteria that = (ContactCriteria) o;
    return (
      Objects.equals(id, that.id) &&
      Objects.equals(title, that.title) &&
      Objects.equals(salutation, that.salutation) &&
      Objects.equals(rank, that.rank) &&
      Objects.equals(gender, that.gender) &&
      Objects.equals(surName, that.surName) &&
      Objects.equals(firstName, that.firstName) &&
      Objects.equals(otherFirstNames, that.otherFirstNames) &&
      Objects.equals(birthName, that.birthName) &&
      Objects.equals(maritalStatus, that.maritalStatus) &&
      Objects.equals(nickName, that.nickName) &&
      Objects.equals(birthDate, that.birthDate) &&
      Objects.equals(birthCity, that.birthCity) &&
      Objects.equals(birthCountry, that.birthCountry) &&
      Objects.equals(nationality, that.nationality) &&
      Objects.equals(addressType, that.addressType) &&
      Objects.equals(street, that.street) &&
      Objects.equals(streetNumber, that.streetNumber) &&
      Objects.equals(postalCode, that.postalCode) &&
      Objects.equals(city, that.city) &&
      Objects.equals(additionalAddressField1, that.additionalAddressField1) &&
      Objects.equals(additionalAddressField2, that.additionalAddressField2) &&
      Objects.equals(communicationType, that.communicationType) &&
      Objects.equals(phoneCountryCode, that.phoneCountryCode) &&
      Objects.equals(phonePrefix, that.phonePrefix) &&
      Objects.equals(phoneNumber, that.phoneNumber) &&
      Objects.equals(comment, that.comment) &&
      Objects.equals(applicationUserId, that.applicationUserId) &&
      Objects.equals(groupTypeId, that.groupTypeId) &&
      Objects.equals(contactFinanceAccountId, that.contactFinanceAccountId) &&
      Objects.equals(distinct, that.distinct)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      id,
      title,
      salutation,
      rank,
      gender,
      surName,
      firstName,
      otherFirstNames,
      birthName,
      maritalStatus,
      nickName,
      birthDate,
      birthCity,
      birthCountry,
      nationality,
      addressType,
      street,
      streetNumber,
      postalCode,
      city,
      additionalAddressField1,
      additionalAddressField2,
      communicationType,
      phoneCountryCode,
      phonePrefix,
      phoneNumber,
      comment,
      applicationUserId,
      groupTypeId,
      contactFinanceAccountId,
      distinct
    );
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ContactCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (salutation != null ? "salutation=" + salutation + ", " : "") +
            (rank != null ? "rank=" + rank + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (surName != null ? "surName=" + surName + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (otherFirstNames != null ? "otherFirstNames=" + otherFirstNames + ", " : "") +
            (birthName != null ? "birthName=" + birthName + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (nickName != null ? "nickName=" + nickName + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (birthCity != null ? "birthCity=" + birthCity + ", " : "") +
            (birthCountry != null ? "birthCountry=" + birthCountry + ", " : "") +
            (nationality != null ? "nationality=" + nationality + ", " : "") +
            (addressType != null ? "addressType=" + addressType + ", " : "") +
            (street != null ? "street=" + street + ", " : "") +
            (streetNumber != null ? "streetNumber=" + streetNumber + ", " : "") +
            (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (additionalAddressField1 != null ? "additionalAddressField1=" + additionalAddressField1 + ", " : "") +
            (additionalAddressField2 != null ? "additionalAddressField2=" + additionalAddressField2 + ", " : "") +
            (communicationType != null ? "communicationType=" + communicationType + ", " : "") +
            (phoneCountryCode != null ? "phoneCountryCode=" + phoneCountryCode + ", " : "") +
            (phonePrefix != null ? "phonePrefix=" + phonePrefix + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (applicationUserId != null ? "applicationUserId=" + applicationUserId + ", " : "") +
            (groupTypeId != null ? "groupTypeId=" + groupTypeId + ", " : "") +
            (contactFinanceAccountId != null ? "contactFinanceAccountId=" + contactFinanceAccountId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
