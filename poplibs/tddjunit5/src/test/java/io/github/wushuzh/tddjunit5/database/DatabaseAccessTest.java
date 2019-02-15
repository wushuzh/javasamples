package io.github.wushuzh.tddjunit5.database;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DatabaseAccessTest {
  @Mock
  private Database database;

  private Credentials credentials = new Credentials("user", "password");

  @Test
  void testUserSuccessfulLogin() {
    when(database.login(credentials)).thenReturn(true);
    Credentials sameCredentials = new Credentials("user", "password");
    assertTrue(database.login(sameCredentials));
  }

}
