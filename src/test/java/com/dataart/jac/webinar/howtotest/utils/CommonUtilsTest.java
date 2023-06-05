package com.dataart.jac.webinar.howtotest.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class CommonUtilsTest {


  @Test
  @DisplayName("To test Static Method")
  @Disabled
  public void testStatic() {
    String actual = CommonUtils.EmptyOrRandom("");
    assertNotEquals("", actual);
    assertEquals("something", CommonUtils.EmptyOrRandom("something"));

    try (MockedStatic<CommonUtils> utilities = Mockito.mockStatic(CommonUtils.class)) {
      utilities.when(() -> CommonUtils.EmptyOrRandom("value")).thenReturn("othervalue");
      assertEquals("othervalue", CommonUtils.EmptyOrRandom("value"));
    }

  }

}