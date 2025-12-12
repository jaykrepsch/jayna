package me.jayna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import me.jayna.domain.enumeration.DocumentStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document extends AbstractCategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "title")
    private String title;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "version")
    private String version;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DocumentStatus status;

    @Column(name = "tags")
    private String tags;

    @Column(name = "keywords", length = 2000)
    private String keywords;

    @Column(name = "content_hash")
    private String contentHash;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "partner")
    private String partner;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "real_estate_number")
    private String realEstateNumber;

    @Column(name = "finance_account_number")
    private String financeAccountNumber;

    @Column(name = "mobility_number")
    private String mobilityNumber;

    // Temporär deaktiviert, da die entsprechenden Tabellen noch nicht existieren
    // @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    // private Set<DocumentContact> documentContacts = new HashSet<>();

    // @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    // private Set<DocumentContract> documentContracts = new HashSet<>();

    // @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    // private Set<DocumentRealEstate> documentRealEstates = new HashSet<>();

    // @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    // private Set<DocumentFinanceAccount> documentFinanceAccounts = new HashSet<>();

    // @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    // private Set<DocumentMobility> documentMobilities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    
    // removed file counters

    public String getTitle() {
        return this.title;
    }

    public Document title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Document fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Document filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return this.fileType;
    }

    public Document fileType(String fileType) {
        this.setFileType(fileType);
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Document fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadDate() {
        return this.uploadDate;
    }

    public Document uploadDate(LocalDateTime uploadDate) {
        this.setUploadDate(uploadDate);
        return this;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LocalDateTime getLastModified() {
        return this.lastModified;
    }

    public Document lastModified(LocalDateTime lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getVersion() {
        return this.version;
    }

    public Document version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DocumentStatus getStatus() {
        return this.status;
    }

    public Document status(DocumentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public String getTags() {
        return this.tags;
    }

    public Document tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public Document keywords(String keywords) {
        this.setKeywords(keywords);
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContentHash() {
        return this.contentHash;
    }

    public Document contentHash(String contentHash) {
        this.setContentHash(contentHash);
        return this;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Document mimeType(String mimeType) {
        this.setMimeType(mimeType);
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Boolean getIsPublic() {
        return this.isPublic;
    }

    public Document isPublic(Boolean isPublic) {
        this.setIsPublic(isPublic);
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public Document expiryDate(LocalDateTime expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public Document documentNumber(String documentNumber) {
        this.setDocumentNumber(documentNumber);
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCustomerNumber() {
        return this.customerNumber;
    }

    public Document customerNumber(String customerNumber) {
        this.setCustomerNumber(customerNumber);
        return this;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getPartner() {
        return this.partner;
    }

    public Document partner(String partner) {
        this.setPartner(partner);
        return this;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getContractNumber() {
        return this.contractNumber;
    }

    public Document contractNumber(String contractNumber) {
        this.setContractNumber(contractNumber);
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getRealEstateNumber() {
        return this.realEstateNumber;
    }

    public Document realEstateNumber(String realEstateNumber) {
        this.setRealEstateNumber(realEstateNumber);
        return this;
    }

    public void setRealEstateNumber(String realEstateNumber) {
        this.realEstateNumber = realEstateNumber;
    }

    public String getFinanceAccountNumber() {
        return this.financeAccountNumber;
    }

    public Document financeAccountNumber(String financeAccountNumber) {
        this.setFinanceAccountNumber(financeAccountNumber);
        return this;
    }

    public void setFinanceAccountNumber(String financeAccountNumber) {
        this.financeAccountNumber = financeAccountNumber;
    }

    public String getMobilityNumber() {
        return this.mobilityNumber;
    }

    public Document mobilityNumber(String mobilityNumber) {
        this.setMobilityNumber(mobilityNumber);
        return this;
    }

    public void setMobilityNumber(String mobilityNumber) {
        this.mobilityNumber = mobilityNumber;
    }

    // Temporär deaktiviert, da die entsprechenden Tabellen noch nicht existieren
    /*
    public Set<DocumentContact> getDocumentContacts() {
        return this.documentContacts;
    }

    public void setDocumentContacts(Set<DocumentContact> documentContacts) {
        if (this.documentContacts != null) {
            this.documentContacts.forEach(i -> i.setDocument(null));
        }
        if (documentContacts != null) {
            documentContacts.forEach(i -> i.setDocument(this));
        }
        this.documentContacts = documentContacts;
    }

    public Document documentContacts(Set<DocumentContact> documentContacts) {
        this.setDocumentContacts(documentContacts);
        return this;
    }

    public Document addDocumentContact(DocumentContact documentContact) {
        this.documentContacts.add(documentContact);
        documentContact.setDocument(this);
        return this;
    }

    public Document removeDocumentContact(DocumentContact documentContact) {
        this.documentContacts.remove(documentContact);
        documentContact.setDocument(null);
        return this;
    }

    public Set<DocumentContract> getDocumentContracts() {
        return this.documentContracts;
    }

    public void setDocumentContracts(Set<DocumentContract> documentContracts) {
        if (this.documentContracts != null) {
            this.documentContracts.forEach(i -> i.setDocument(null));
        }
        if (documentContracts != null) {
            documentContracts.forEach(i -> i.setDocument(this));
        }
        this.documentContracts = documentContracts;
    }

    public Document documentContracts(Set<DocumentContract> documentContracts) {
        this.setDocumentContracts(documentContracts);
        return this;
    }

    public Document addDocumentContract(DocumentContract documentContract) {
        this.documentContracts.add(documentContract);
        documentContract.setDocument(this);
        return this;
    }

    public Document removeDocumentContract(DocumentContract documentContract) {
        this.documentContracts.remove(documentContract);
        documentContract.setDocument(null);
        return this;
    }

    public Set<DocumentRealEstate> getDocumentRealEstates() {
        return this.documentRealEstates;
    }

    public void setDocumentRealEstates(Set<DocumentRealEstate> documentRealEstates) {
        if (this.documentRealEstates != null) {
            this.documentRealEstates.forEach(i -> i.setDocument(null));
        }
        if (documentRealEstates != null) {
            documentRealEstates.forEach(i -> i.setDocument(this));
        }
        this.documentRealEstates = documentRealEstates;
    }

    public Document documentRealEstates(Set<DocumentRealEstate> documentRealEstates) {
        this.setDocumentRealEstates(documentRealEstates);
        return this;
    }

    public Document addDocumentRealEstate(DocumentRealEstate documentRealEstate) {
        this.documentRealEstates.add(documentRealEstate);
        documentRealEstate.setDocument(this);
        return this;
    }

    public Document removeDocumentRealEstate(DocumentRealEstate documentRealEstate) {
        this.documentRealEstates.remove(documentRealEstate);
        documentRealEstate.setDocument(null);
        return this;
    }

    public Set<DocumentFinanceAccount> getDocumentFinanceAccounts() {
        return this.documentFinanceAccounts;
    }

    public void setDocumentFinanceAccounts(Set<DocumentFinanceAccount> documentFinanceAccounts) {
        if (this.documentFinanceAccounts != null) {
            this.documentFinanceAccounts.forEach(i -> i.setDocument(null));
        }
        if (documentFinanceAccounts != null) {
            documentFinanceAccounts.forEach(i -> i.setDocument(this));
        }
        this.documentFinanceAccounts = documentFinanceAccounts;
    }

    public Document documentFinanceAccounts(Set<DocumentFinanceAccount> documentFinanceAccounts) {
        this.setDocumentFinanceAccounts(documentFinanceAccounts);
        return this;
    }

    public Document addDocumentFinanceAccount(DocumentFinanceAccount documentFinanceAccount) {
        this.documentFinanceAccounts.add(documentFinanceAccount);
        documentFinanceAccount.setDocument(this);
        return this;
    }

    public Document removeDocumentFinanceAccount(DocumentFinanceAccount documentFinanceAccount) {
        this.documentFinanceAccounts.remove(documentFinanceAccount);
        documentFinanceAccount.setDocument(null);
        return this;
    }

    public Set<DocumentMobility> getDocumentMobilities() {
        return this.documentMobilities;
    }

    public void setDocumentMobilities(Set<DocumentMobility> documentMobilities) {
        if (this.documentMobilities != null) {
            this.documentMobilities.forEach(i -> i.setDocument(null));
        }
        if (documentMobilities != null) {
            documentMobilities.forEach(i -> i.setDocument(this));
        }
        this.documentMobilities = documentMobilities;
    }

    public Document documentMobilities(Set<DocumentMobility> documentMobilities) {
        this.setDocumentMobilities(documentMobilities);
        return this;
    }

    public Document addDocumentMobility(DocumentMobility documentMobility) {
        this.documentMobilities.add(documentMobility);
        documentMobility.setDocument(this);
        return this;
    }

    public Document removeDocumentMobility(DocumentMobility documentMobility) {
        this.documentMobilities.remove(documentMobility);
        documentMobility.setDocument(null);
        return this;
    }
    */

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // removed file counter accessors

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return getId() != null && getId().equals(((Document) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", uploadDate='" + getUploadDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", version='" + getVersion() + "'" +
            ", status='" + getStatus() + "'" +
            ", tags='" + getTags() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", contentHash='" + getContentHash() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", isPublic='" + getIsPublic() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", partner='" + getPartner() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", realEstateNumber='" + getRealEstateNumber() + "'" +
            ", financeAccountNumber='" + getFinanceAccountNumber() + "'" +
            ", mobilityNumber='" + getMobilityNumber() + "'" +
            "}";
    }
}
