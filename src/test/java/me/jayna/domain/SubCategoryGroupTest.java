package me.jayna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jayna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubCategoryGroupTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(SubCategoryGroup.class);
    SubCategoryGroup subCategoryGroup1 = new SubCategoryGroup();
    subCategoryGroup1.setId(1L);
    SubCategoryGroup subCategoryGroup2 = new SubCategoryGroup();
    subCategoryGroup2.setId(subCategoryGroup1.getId());
    assertThat(subCategoryGroup1).isEqualTo(subCategoryGroup2);
    subCategoryGroup2.setId(2L);
    assertThat(subCategoryGroup1).isNotEqualTo(subCategoryGroup2);
    subCategoryGroup1.setId(null);
    assertThat(subCategoryGroup1).isNotEqualTo(subCategoryGroup2);
  }
}
