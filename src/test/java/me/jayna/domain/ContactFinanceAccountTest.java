package me.jayna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jayna.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactFinanceAccountTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(ContactFinanceAccount.class);
    ContactFinanceAccount contactFinanceAccount1 = new ContactFinanceAccount();
    contactFinanceAccount1.setId(1L);
    ContactFinanceAccount contactFinanceAccount2 = new ContactFinanceAccount();
    contactFinanceAccount2.setId(contactFinanceAccount1.getId());
    assertThat(contactFinanceAccount1).isEqualTo(contactFinanceAccount2);
    contactFinanceAccount2.setId(2L);
    assertThat(contactFinanceAccount1).isNotEqualTo(contactFinanceAccount2);
    contactFinanceAccount1.setId(null);
    assertThat(contactFinanceAccount1).isNotEqualTo(contactFinanceAccount2);
  }
}
