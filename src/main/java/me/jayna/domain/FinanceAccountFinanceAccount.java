package me.jayna.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "finance_account_finance_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FinanceAccountFinanceAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "finance_account_id")
    private FinanceAccount financeAccount;

    @ManyToOne
    @JoinColumn(name = "related_finance_account_id")
    private FinanceAccount relatedFinanceAccount;

    @Column(name = "relation_type")
    private String relationType;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FinanceAccount getFinanceAccount() {
        return financeAccount;
    }

    public void setFinanceAccount(FinanceAccount financeAccount) {
        this.financeAccount = financeAccount;
    }

    public FinanceAccount getRelatedFinanceAccount() {
        return relatedFinanceAccount;
    }

    public void setRelatedFinanceAccount(FinanceAccount relatedFinanceAccount) {
        this.relatedFinanceAccount = relatedFinanceAccount;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 