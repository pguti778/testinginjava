package com.dataart.jac.webinar.howtotest.service.b.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MyFirstJUnitJupiterTests {

  @Test
  public void addition() {
    assertEquals(2, Integer.parseInt("1")+1);
  }

}