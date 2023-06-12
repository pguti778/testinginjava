package com.dataart.jac.webinar.howtotest.service.c.mockito;

import static com.dataart.jac.webinar.howtotest.service.c.mockito.ManglingService.SALT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;

import com.dataart.jac.webinar.howtotest.gateway.RemoteMD5Client;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManglingServiceInjectTest {

  // Initialize mock - The old way
//  public ManglingServiceTest() {
//    MockitoAnnotations.openMocks(this);
//  }

  @Mock
  RemoteMD5Client remoteMD5ClientInstance;

  @InjectMocks
  ManglingService manglingServiceMockInjected;

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

    //
    Mockito.when(remoteMD5Client.md5sum(isNull())).thenThrow(new IllegalStateException(""));
    Mockito.when(remoteMD5Client.md5sum(anyString())).thenAnswer(invocation ->
        invocation.getArgument(0, String.class).toUpperCase());

    String result = manglingService.saltedMD5("");
    assertNotNull(result);

    assertThrows(IllegalStateException.class, () -> manglingService.saltedMD5(null));

  }

}