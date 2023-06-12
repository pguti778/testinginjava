package com.dataart.jac.webinar.howtotest.service.b.junit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExceptionTest {

  @Test
  @DisplayName("Test Exception")
  public void testException() {
    Exception exception = assertThrows(Exception.class, () -> { Objects.requireNonNull(null); } );
    //exception.printStackTrace();
    System.out.println(exception.getMessage());
    assertNotNull(exception);

  }


}
