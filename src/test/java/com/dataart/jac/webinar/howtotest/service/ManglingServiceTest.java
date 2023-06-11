package com.dataart.jac.webinar.howtotest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

  @InjectMocks
  ManglingService manglingServiceMockInjected;

  @Test
  @DisplayName("Testing and mocking a service")
  public void testEncryptSimple() {
    // Mock instance
    RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);

    // Conditions -> Arrange - Given
    lenient().when(remoteMD5Client.md5sum("simple")).thenReturn("empty");

    // call the real method
    //Mockito.when(remoteMD5Client.md5sum("complex")).thenCallRealMethod();

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals("SIMPLE" + ManglingService.SALT, result);
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
    assertEquals("SIMPLE" + ManglingService.SALT, result);
  }

  @Test
  @DisplayName("Test Mocked objects")
  public void testMockedInjectedObject() {
    // Conditions -> Arrange - Given
    Mockito.when(this.remoteMD5Client.md5sum("simple" + ManglingService.SALT)).thenReturn("empty");

    // The actual Execution ->  Act - When
    String result = this.manglingServiceMockInjected.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals("empty", result);
  }

  @Test
  @DisplayName("Test Exception")
  public void testException() {
    Exception exception = assertThrows(Exception.class, () -> { Objects.requireNonNull(null); } );
    //exception.printStackTrace();
    System.out.println(exception.getMessage());
    assertNotNull(exception);

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