package me.jayna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jayna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractRealestateTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ContractRealestate.class);
    ContractRealestate contractRealestate1 = new ContractRealestate();
    contractRealestate1.setId(1L);
    ContractRealestate contractRealestate2 = new ContractRealestate();
    contractRealestate2.setId(contractRealestate1.getId());
    assertThat(contractRealestate1).isEqualTo(contractRealestate2);
    contractRealestate2.setId(2L);
    assertThat(contractRealestate1).isNotEqualTo(contractRealestate2);
    contractRealestate1.setId(null);
    assertThat(contractRealestate1).isNotEqualTo(contractRealestate2);
  }
}
