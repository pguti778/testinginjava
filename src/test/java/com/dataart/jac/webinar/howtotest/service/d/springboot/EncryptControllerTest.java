package com.dataart.jac.webinar.howtotest.service.d.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * To test SpringCloud Funcion, you have to initialize the whole SpringBoot app wth the @SpringootTrst and
 * @AutoconfiguredMockMvc to have the MockMvc bean in the app context to autowire.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EncryptControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testSayHello() {
    try {
      this.mockMvc.perform(MockMvcRequestBuilders.get("/sayHello"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andDo(MockMvcResultHandlers.print());
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