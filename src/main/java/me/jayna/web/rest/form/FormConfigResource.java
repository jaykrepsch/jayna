package me.jayna.web.rest.form;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.jayna.domain.ApplicationUser;
import me.jayna.domain.GroupType;
import me.jayna.domain.form.Config;
import me.jayna.domain.form.Part;
import me.jayna.domain.form.Field;
import me.jayna.repository.GroupTypeRepository;
import me.jayna.repository.form.ConfigRepository;
import me.jayna.repository.form.FieldRepository;
import me.jayna.repository.form.PartRepository;
import me.jayna.service.UserService;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class FormConfigResource {

  private final Logger log = LoggerFactory.getLogger(FormConfigResource.class);

  private static final String ENTITY_NAME = "form.config";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ConfigRepository configRepository;

  private final PartRepository partRepository;

  private final FieldRepository fieldRepository;

  private final GroupTypeRepository groupTypeRepository;

  private final UserService userService;

  public FormConfigResource(ConfigRepository configRepository, PartRepository partRepository,
      FieldRepository fieldRepository, GroupTypeRepository groupTypeRepository, UserService userService) {
    this.configRepository = configRepository;
    this.partRepository = partRepository;
    this.fieldRepository = fieldRepository;
    this.groupTypeRepository = groupTypeRepository;
    this.userService = userService;
  }

  @PostMapping("/form-configs")
  public ResponseEntity<Config> saveFormConfig(@RequestBody Config config) throws URISyntaxException {
    log.debug("REST request to save FormConfig : {}", config);

    final Set<Part> parts = new HashSet<>(config.getParts());
    config.setParts(new HashSet<>());

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();

    config.setApplicationUser(applicationUser);
    final Config result = configRepository.save(config);

    for (Part part : parts) {

      final Set<Field> fields = new HashSet<>(part.getFields());

      part.setFields(new HashSet<>());
      part.setConfig(result);

      final Part resultPart = partRepository.save(part);

      for (Field field : fields) {
        field.setPart(resultPart);
        final Field resultField = fieldRepository.save(field);
        resultPart.getFields().add(resultField);
      }
      result.getParts().add(resultPart);
    }

    return ResponseEntity
        .created(new URI("/api/configs/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/form-configs/{id}")
  public ResponseEntity<Config> updateFormConfig(@RequestBody Config config) throws URISyntaxException {

    final Config result = null;

    return ResponseEntity
        .created(new URI("/api/configs/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @GetMapping("/form-configs")
  public List<Config> getFormConfigsForUser() {

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    final List<Config> configs = configRepository.findByApplicationUser(applicationUser);

    final List<Config> mappedGroupTypes = groupTypeRepository
        .findAllByCreatedByInOrderByNameAsc(Arrays.asList(applicationUser.getInternalUser().getLogin()))
        .stream()
        .filter(gt -> configRepository.findOneByGroupType(gt).isEmpty())
        .map(gt -> new Config(null, gt, new HashSet<>()))
        .collect(Collectors.toList());

    configs.addAll(mappedGroupTypes);

    return configs;
  }

  @GetMapping("/form-configs/{id}")
  public ResponseEntity<Config> getFormConfig(@PathVariable("id") long id) {

    final ApplicationUser applicationUser = userService.getApplicationUser().orElseThrow();
    final Config config = configRepository.findOneByIdAndApplicationUser(id, applicationUser).orElseThrow();

    return ResponseEntity.ok(config);
  }

  @GetMapping("/group-types/{groupTypeId}/form-configs")
  public ResponseEntity<Config> getFormConfigForGroupType(@PathVariable("groupTypeId") Long groupTypeId)
      throws NoSuchElementException {
    log.debug("REST request to get FormConfig for GroupType with ID: {}", groupTypeId);

    try {
      final GroupType groupType = groupTypeRepository.findById(groupTypeId).orElseThrow();
      final Config config = configRepository.findOneByGroupType(groupType).orElseThrow();

      return ResponseEntity.ok(config);
    } catch (NoSuchElementException e) {
      log.error(e.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

}
