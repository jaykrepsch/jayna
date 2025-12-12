package me.jayna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jayna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupTypeTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(GroupType.class);
    GroupType groupType1 = new GroupType();
    groupType1.setId(1L);
    GroupType groupType2 = new GroupType();
    groupType2.setId(groupType1.getId());
    assertThat(groupType1).isEqualTo(groupType2);
    groupType2.setId(2L);
    assertThat(groupType1).isNotEqualTo(groupType2);
    groupType1.setId(null);
    assertThat(groupType1).isNotEqualTo(groupType2);
  }
}
