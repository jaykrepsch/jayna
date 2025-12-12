package me.jayna.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import me.jayna.service.dto.EnumDTO;
import me.jayna.service.dto.EnumListDTO;
import me.jayna.web.rest.errors.BadRequestAlertException;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * REST controller for managing {@link me.jayna.domain.Category}.
 */
@RestController
@RequestMapping("/api/config")
public class ConfigResource {

  private final Logger log = LoggerFactory.getLogger(ConfigResource.class);

  private static final String ENTITY_NAME = "config";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private static final Map<String, List<EnumListDTO>> ENUM_CACHE = new ConcurrentHashMap<>();
  private static final Map<String, List<EnumDTO>> ENUM_LIST_CACHE = new ConcurrentHashMap<>();
  private static final String ENUM_PACKAGE = "me.jayna.domain.enumeration";

  /**
   * {@code POST  /categories} : Create a new category.
   *
   * @param category the category to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new category, or with status {@code 400 (Bad Request)} if
   *         the category has already an ID.
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws URISyntaxException     if the Location URI syntax is incorrect.
   */
  @GetMapping("/enums")
  public List<EnumListDTO> getEnums() throws IOException {
    log.debug("REST request to get list of enums.");
    
    return ENUM_CACHE.computeIfAbsent("all", key -> {
      try {
        final Set<Class<?>> clazzes = findAllClassesInPackage(ENUM_PACKAGE);
        log.debug("number of classes {}", clazzes.size());

        return clazzes.stream().map(clz -> {
          final List<EnumDTO> items = Arrays
              .asList(getNames(clz))
              .stream()
              .map(n -> new EnumDTO(n))
              .collect(Collectors.toList());
          return new EnumListDTO(clz.getSimpleName(), items);
        }).collect(Collectors.toList());
      } catch (IOException e) {
        log.error("Error loading enums", e);
        return List.of();
      }
    });
  }

  @GetMapping("/enums/{name}")
  public List<EnumDTO> getEnumsListForName(@PathVariable String name) throws ClassNotFoundException {
    log.debug("REST request to get list of enums for: {}", name);
    if (StringUtils.isEmpty(name)) {
      throw new BadRequestAlertException("Request Param name is missing.", ENTITY_NAME, "parammissing");
    }

    return ENUM_LIST_CACHE.computeIfAbsent(name, key -> {
      try {
        Class<?> clz = Class.forName(ENUM_PACKAGE + "." + name);
        return Arrays
            .asList(getNames(clz))
            .stream()
            .map(n -> new EnumDTO(n))
            .collect(Collectors.toList());
      } catch (ClassNotFoundException e) {
        log.error("Enum class not found: {}", name, e);
        return List.of();
      }
    });
  }

  private String[] getNames(Class<?> e) {
    return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
  }

  private Set<Class<?>> findAllClassesInPackage(String packageName) throws IOException {
    final Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages(packageName)
        .filterInputsBy(input -> input.endsWith(".class")));
    return reflections.getSubTypesOf(Enum.class)
        .stream()
        .collect(Collectors.toSet());
  }

  @PostConstruct
  public void initializeEnums() {
    try {
      log.info("Initializing enum cache...");
      getEnums(); // This will populate the cache
      log.info("Enum cache initialized successfully");
    } catch (IOException e) {
      log.error("Failed to initialize enum cache", e);
    }
  }
}
