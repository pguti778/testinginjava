package com.dataart.jac.webinar.howtotest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManglingServiceTest {

  // Initialize mock - The old way
//  public ManglingServiceTest() {
//    MockitoAnnotations.openMocks(this);
//  }
  @Mock
  RemoteMD5Client remoteMD5ClientInstance;

  @Test
  @DisplayName("Testing and mocking a service")
  public void testEncryptSimple() {
    // Mock instance
    RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);

    // Conditions -> Arrange - Given
    Mockito.when(remoteMD5Client.md5sum("simple")).thenReturn("empty");

    //
    Mockito.when(remoteMD5Client.md5sum("complex")).thenCallRealMethod();

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals(result, "SIMPLE" + ManglingService.SALT);
  }

  @Spy
  RemoteMD5Client remoteMD5Client;

  @Test
  @DisplayName("Testing and mocking a service")
  public void testEncryptSpy() {
    // Conditions -> Arrange - Given
    Mockito.when(remoteMD5Client.md5sum("simple")).thenReturn("empty");

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals(result, "SIMPLE" + ManglingService.SALT);
  }



  public void testEncryptNull() {
    // Mock instance
    RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    Mockito.when(remoteMD5Client.md5sum(isNull())).thenThrow(new IllegalStateException(""));
    Mockito.when(remoteMD5Client.md5sum("")).thenReturn("empty");
    Mockito.when(remoteMD5Client.md5sum(anyString())).thenAnswer(invocation ->
        invocation.getArgument(0, String.class).toUpperCase());

    String result = manglingService.saltedMD5("");
    assertNotNull(result);

    assertThrows(IllegalStateException.class, () -> manglingService.saltedMD5(null));

  }

}