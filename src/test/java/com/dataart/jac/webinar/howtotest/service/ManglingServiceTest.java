package com.dataart.jac.webinar.howtotest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ManglingServiceTest {

  public ManglingServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Testing and mocking a service")
  public void testEncryptSimple() {

    // Mocke instance
    RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);

    // Conditions
    Mockito.when(remoteMD5Client.md5sum(isNull())).thenThrow(new IllegalStateException(""));
    Mockito.when(remoteMD5Client.md5sum("")).thenReturn("empty");
    Mockito.when(remoteMD5Client.md5sum(anyString())).thenAnswer(invocation ->
          invocation.getArgument(0, String.class).toUpperCase()
    );

    // Real service
    ManglingService manglingService = new ManglingService(remoteMD5Client);
    String result = manglingService.saltedMD5("somevalue");
    assertEquals(result , "SOMEVALUE" + ManglingService.SALT);

    result = manglingService.saltedMD5("");
    assertNotNull(result);

    assertThrows(IllegalStateException.class, () -> manglingService.saltedMD5(null));

  }


}