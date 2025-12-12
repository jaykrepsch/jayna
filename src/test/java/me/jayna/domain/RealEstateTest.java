package me.jayna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jayna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RealEstateTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(RealEstate.class);
    RealEstate realEstate1 = new RealEstate();
    realEstate1.setId(1L);
    RealEstate realEstate2 = new RealEstate();
    realEstate2.setId(realEstate1.getId());
    assertThat(realEstate1).isEqualTo(realEstate2);
    realEstate2.setId(2L);
    assertThat(realEstate1).isNotEqualTo(realEstate2);
    realEstate1.setId(null);
    assertThat(realEstate1).isNotEqualTo(realEstate2);
  }
}
