package com.dataart.jac.webinar.howtotest.service.d.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import com.dataart.jac.webinar.howtotest.service.c.mockito.ManglingService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ManglingService.class)
//@SpringJUnitConfig
@RequiredArgsConstructor
@TestInstance(Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = ALL)
class ManglingServiceBeanTest {

  @Nullable
  @MockBean
  RemoteMD5Client remoteMD5ClientInstanceBean;

  @BeforeAll
  void init() {
    // Arrange
    Mockito.when(remoteMD5ClientInstanceBean.md5sum(any())).thenReturn("SOMEVALUE");
  }

  @Test
  public void simpleTest(@Autowired ManglingService manglingService) {
    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals("SOMEVALUE", result);

  }


}