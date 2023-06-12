package com.dataart.jac.webinar.howtotest.service.d.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class EncryptControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testSayHello() {
    try {
      // Spring Cloud Function is not created when using the WebMvcTest
      this.mockMvc.perform(MockMvcRequestBuilders.get("/sayHello"))
          .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testDoATest() {
    try {
      this.mockMvc.perform(MockMvcRequestBuilders.get("/do-a-test"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andDo(MockMvcResultHandlers.print());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }


}