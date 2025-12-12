package me.jayna.web.rest.vm;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import me.jayna.domain.form.Config;
import me.jayna.domain.form.CustomFormInput;

public class CustomFormInputVM {

  private Config config;

  private JsonNode payload;

  public CustomFormInputVM() {
  }

  public CustomFormInputVM(CustomFormInput customFormInput, ObjectMapper mapper)
      throws SQLException, IOException {
    setConfig(customFormInput.getConfig());

    final JsonNode node = mapper
        .readTree(customFormInput.getPayload());

    ((ObjectNode) node).put("id", customFormInput.getId());
    ((ObjectNode) node).put("label", customFormInput.getDescription() != null ? customFormInput.getDescription() : "");
    ((ObjectNode) node).put("entityState", customFormInput.getEntityState().name());
    ((ObjectNode) node).put("comment", customFormInput.getComment());
    ((ObjectNode) node).put("description", customFormInput.getDescription());
    ((ObjectNode) node).put("createdDate", customFormInput.getCreatedDate().toString());
    ((ObjectNode) node).put("lastModifiedBy", customFormInput.getLastModifiedBy());
    ((ObjectNode) node).put("lastModifiedDate", customFormInput.getLastModifiedDate().toString());

    setPayload(node);
  }

  public Config getConfig() {
    return this.config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  public JsonNode getPayload() {
    return this.payload;
  }

  public void setPayload(JsonNode payload) {
    this.payload = payload;
  }
}
