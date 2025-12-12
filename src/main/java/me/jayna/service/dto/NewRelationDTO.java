package me.jayna.service.dto;

public class NewRelationDTO {

    private String entityName;
    private Object relationData;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Object getRelationData() {
        return relationData;
    }

    public void setRelationData(Object relationData) {
        this.relationData = relationData;
    }
}
