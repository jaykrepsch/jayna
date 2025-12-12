package me.jayna.web.rest.form;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.GroupType;
import me.jayna.domain.User;
import me.jayna.domain.enumeration.EntityState;
import me.jayna.domain.form.Config;
import me.jayna.domain.form.CustomFormInput;
import me.jayna.repository.GroupTypeRepository;
import me.jayna.repository.form.ConfigRepository;
import me.jayna.repository.form.CustomFormInputRepository;
import me.jayna.service.UserService;
import me.jayna.web.rest.errors.BadRequestAlertException;
import me.jayna.web.rest.vm.CustomFormInputVM;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
@Transactional
public class CustomFormInputResource {

  private final Logger log = LoggerFactory.getLogger(FormConfigResource.class);

  private static final String ENTITY_NAME = "custom.form.input";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final CustomFormInputRepository customFormInputRepository;

  private final ConfigRepository configRepository;

  private final ObjectMapper mapper = new ObjectMapper();

  private final UserService userService;

  public CustomFormInputResource(CustomFormInputRepository customFormInputRepository,
      ConfigRepository configRepository, UserService userService) {
    this.customFormInputRepository = customFormInputRepository;
    this.configRepository = configRepository;
    this.userService = userService;
  }

  @PostMapping("/custom-form-inputs")
  public ResponseEntity<CustomFormInput> saveFormConfig(@RequestBody CustomFormInputVM customFormInputVM)
      throws URISyntaxException, SerialException, SQLException, JsonProcessingException {
    log.debug("REST request to save CustomFormInput : {}", customFormInputVM);

    // Validierung der Eingabedaten
    if (customFormInputVM == null) {
      throw new BadRequestAlertException("CustomFormInputVM cannot be null", ENTITY_NAME, "nullinput");
    }
    
    if (customFormInputVM.getConfig() == null || customFormInputVM.getConfig().getId() == null) {
      throw new BadRequestAlertException("Config is required", ENTITY_NAME, "configrequired");
    }
    
    if (customFormInputVM.getPayload() == null) {
      throw new BadRequestAlertException("Payload is required", ENTITY_NAME, "payloadrequired");
    }

    final CustomFormInput customFormInput = new CustomFormInput();
    final User user = userService.getUserWithAuthorities().orElseThrow();

    try {
      final Config config = configRepository.findById(customFormInputVM.getConfig().getId()).orElseThrow();
      customFormInput.setConfig(config);
      customFormInput.setPayload(mapper.writeValueAsBytes(customFormInputVM.getPayload()));
      customFormInput.setGroupType(config.getGroupType());
      customFormInput.setEntityState(EntityState.ACTIVE);
      customFormInput.setLastModifiedBy(user.getLogin());

      final CustomFormInput result = customFormInputRepository.save(customFormInput);
      
      log.debug("CustomFormInput saved successfully with ID: {}", result.getId());

      return ResponseEntity
          .created(new URI("/api/custom-form-inputs/" + result.getId()))
          .headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
              ENTITY_NAME, result.getId().toString()))
          .body(result);
    } catch (Exception e) {
      log.error("Error saving CustomFormInput: {}", e.getMessage(), e);
      throw new BadRequestAlertException("Error saving CustomFormInput: " + e.getMessage(), ENTITY_NAME, "saveerror");
    }
  }

  @GetMapping("/custom-form-inputs/{id}")
  public ResponseEntity<CustomFormInputVM> getCustomFormConfig(@PathVariable("id") Long id)
      throws SQLException, IOException {
    log.debug("REST request to get CustomFormInput by ID : {}", id);

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    final CustomFormInput customFormInput = customFormInputRepository.findById(id).orElseThrow();

    if (customFormInput.getConfig().getApplicationUser().getId() != applicationUser.getId()) {
      throw new BadRequestAlertException(
          "You are not allowed to access this entity.",
          ENTITY_NAME,
          "notallowed");
    }

    final CustomFormInputVM vm = new CustomFormInputVM(customFormInput, mapper);

    return ResponseEntity.ok(vm);
  }

  @PutMapping("/custom-form-inputs/{id}")
  public ResponseEntity<CustomFormInputVM> updateCustomFormInput(@PathVariable("id") long id,
      @RequestBody CustomFormInputVM vm) throws SerialException, SQLException, IOException {

    log.debug("REST request to update CustomFormInput : {}, {}", id, vm);

    // Validierung der Eingabedaten
    if (vm == null) {
      throw new BadRequestAlertException("CustomFormInputVM cannot be null", ENTITY_NAME, "nullinput");
    }
    
    if (vm.getPayload() == null) {
      throw new BadRequestAlertException("Payload is required", ENTITY_NAME, "payloadrequired");
    }

    if (!customFormInputRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    try {
      final CustomFormInput customFormInput = customFormInputRepository.findById(id).orElseThrow();
      customFormInput.setPayload(mapper.writeValueAsBytes(vm.getPayload()));

      final CustomFormInput result = customFormInputRepository.save(customFormInput);
      final CustomFormInputVM customFormInputVM = new CustomFormInputVM(result, mapper);
      
      log.debug("CustomFormInput updated successfully with ID: {}", result.getId());

      return ResponseEntity.ok(customFormInputVM);
    } catch (Exception e) {
      log.error("Error updating CustomFormInput: {}", e.getMessage(), e);
      throw new BadRequestAlertException("Error updating CustomFormInput: " + e.getMessage(), ENTITY_NAME, "updateerror");
    }
  }

  @GetMapping("/custom-form-inputs")
  public List<CustomFormInputVM> getCustomFormConfigs(
      @RequestParam(name = "categoryId", required = false) Long categoryId) {
    log.debug("REST request to get all CustomFormInputs for categoryId : {}", categoryId);

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    final List<CustomFormInput> tmpResult = new ArrayList<>();

    if (categoryId != null) {
      tmpResult.addAll(customFormInputRepository.findAllByCategoryIdAndApplicationUserId(
          categoryId,
          applicationUser.getId()));
    } else {
      tmpResult.addAll(customFormInputRepository.findAllByApplicationUserId(
          applicationUser.getId()));
    }

    final List<CustomFormInputVM> result = tmpResult.stream().map(cfi -> {
      try {
        return new CustomFormInputVM(cfi, mapper);
      } catch (SQLException | IOException e) {
        log.error(e.getMessage());
      }
      return null;
    }).collect(Collectors.toList());

    return result;
  }

}
