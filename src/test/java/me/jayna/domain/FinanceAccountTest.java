package me.jayna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jayna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinanceAccountTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(FinanceAccount.class);
    FinanceAccount financeAccount1 = new FinanceAccount();
    financeAccount1.setId(1L);
    FinanceAccount financeAccount2 = new FinanceAccount();
    financeAccount2.setId(financeAccount1.getId());
    assertThat(financeAccount1).isEqualTo(financeAccount2);
    financeAccount2.setId(2L);
    assertThat(financeAccount1).isNotEqualTo(financeAccount2);
    financeAccount1.setId(null);
    assertThat(financeAccount1).isNotEqualTo(financeAccount2);
  }
}
