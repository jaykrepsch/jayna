package me.jayna.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "mobility_mobility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MobilityMobility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mobility_id")
    private Mobility mobility;

    @ManyToOne
    @JoinColumn(name = "related_mobility_id")
    private Mobility relatedMobility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public Mobility getRelatedMobility() {
        return relatedMobility;
    }

    public void setRelatedMobility(Mobility relatedMobility) {
        this.relatedMobility = relatedMobility;
    }
} 