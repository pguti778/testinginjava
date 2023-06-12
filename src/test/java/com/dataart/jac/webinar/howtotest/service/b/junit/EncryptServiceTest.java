package com.dataart.jac.webinar.howtotest.service.b.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.dataart.jac.webinar.howtotest.service.b.junit.EncryptService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Basic testing.
 */
class EncryptServiceTest {

  private EncryptService encryptService;

  /***
   * Tests are classes and can have all classes things: relations, initialization, inheritance.
   */
  public EncryptServiceTest() {
    this.encryptService = new EncryptService();
  }

  @Test
  @DisplayName("Positive test")
  void testEncryptPositive() {
    String password = "mypassword";
    assertEquals(this.encryptService.encryptPassword(password), password+ EncryptService.KEY);
  }

  @Test
  @DisplayName("Negative test")
  void testEncryptNegative() {
    String password = "mypassword";
    assertNotEquals(this.encryptService.encryptPassword(password), password);
  }

  public void nonTestMethod() {
    // This is not a test method.
  }

}