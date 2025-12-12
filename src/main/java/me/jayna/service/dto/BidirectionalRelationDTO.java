package me.jayna.service.dto;

import java.io.Serializable;

/**
 * A DTO for bidirectional relations.
 */
public class BidirectionalRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String sourceEntity;
    private String targetEntity;
    private Long sourceEntityId;
    private Long targetEntityId;
    private String relationType;
    private String displayName;
    private String description;
    private boolean bidirectional;

    public BidirectionalRelationDTO() {
        // Empty constructor needed for Jackson
    }

    public BidirectionalRelationDTO(
        Long id,
        String sourceEntity,
        String targetEntity,
        Long sourceEntityId,
        Long targetEntityId,
        String relationType,
        String displayName,
        String description,
        boolean bidirectional
    ) {
        this.id = id;
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.sourceEntityId = sourceEntityId;
        this.targetEntityId = targetEntityId;
        this.relationType = relationType;
        this.displayName = displayName;
        this.description = description;
        this.bidirectional = bidirectional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Long getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(Long sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public Long getTargetEntityId() {
        return targetEntityId;
    }

    public void setTargetEntityId(Long targetEntityId) {
        this.targetEntityId = targetEntityId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BidirectionalRelationDTO)) {
            return false;
        }

        BidirectionalRelationDTO that = (BidirectionalRelationDTO) o;

        if (bidirectional != that.bidirectional) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (sourceEntity != null ? !sourceEntity.equals(that.sourceEntity) : that.sourceEntity != null) {
            return false;
        }
        if (targetEntity != null ? !targetEntity.equals(that.targetEntity) : that.targetEntity != null) {
            return false;
        }
        if (sourceEntityId != null ? !sourceEntityId.equals(that.sourceEntityId) : that.sourceEntityId != null) {
            return false;
        }
        if (targetEntityId != null ? !targetEntityId.equals(that.targetEntityId) : that.targetEntityId != null) {
            return false;
        }
        if (relationType != null ? !relationType.equals(that.relationType) : that.relationType != null) {
            return false;
        }
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) {
            return false;
        }
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sourceEntity != null ? sourceEntity.hashCode() : 0);
        result = 31 * result + (targetEntity != null ? targetEntity.hashCode() : 0);
        result = 31 * result + (sourceEntityId != null ? sourceEntityId.hashCode() : 0);
        result = 31 * result + (targetEntityId != null ? targetEntityId.hashCode() : 0);
        result = 31 * result + (relationType != null ? relationType.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (bidirectional ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BidirectionalRelationDTO{" +
            "id=" + id +
            ", sourceEntity='" + sourceEntity + '\'' +
            ", targetEntity='" + targetEntity + '\'' +
            ", sourceEntityId=" + sourceEntityId +
            ", targetEntityId=" + targetEntityId +
            ", relationType='" + relationType + '\'' +
            ", displayName='" + displayName + '\'' +
            ", description='" + description + '\'' +
            ", bidirectional=" + bidirectional +
            '}';
    }
} 