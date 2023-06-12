package com.dataart.jac.webinar.howtotest.service.c.mockito;

import static com.dataart.jac.webinar.howtotest.service.c.mockito.ManglingService.SALT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

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
  //f@Disabled
  public void should_returnCapitals_when_sentSimple() {
    // Mock instance
    //RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);

    // Conditions -> Arrange - Given
    lenient().when(remoteMD5ClientInstance.md5sum("simple")).thenReturn("empty");

    // call the real method
    //Mockito.when(remoteMD5Client.md5sum("complex")).thenCallRealMethod();
    //Mockito.when(remoteMD5Client.md5sum(any())).thenCallRealMethod();

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5ClientInstance);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertNotEquals("SIMPLE" + SALT, result);
  }

  @Spy
  RemoteMD5Client remoteMD5ClientSpied;

  @Test
  @DisplayName("Testing and mocking a service")
  public void givenMd5SumMocked_WhenSaltMD5_ThenEmptyReturned() {
    // Conditions -> Arrange - Given
    Mockito.when(remoteMD5ClientSpied.md5sum("simple" + SALT)).thenReturn("empty");

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5ClientSpied);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals("empty", result);
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