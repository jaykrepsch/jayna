package me.jayna.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "real_estate_real_estate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RealEstateRealEstate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "real_estate_id")
    private RealEstate realestate;

    @ManyToOne
    @JoinColumn(name = "related_real_estate_id")
    private RealEstate relatedRealEstate;

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

    public RealEstate getRealestate() {
        return realestate;
    }

    public void setRealestate(RealEstate realestate) {
        this.realestate = realestate;
    }

    public RealEstate getRelatedRealEstate() {
        return relatedRealEstate;
    }

    public void setRelatedRealEstate(RealEstate relatedRealEstate) {
        this.relatedRealEstate = relatedRealEstate;
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