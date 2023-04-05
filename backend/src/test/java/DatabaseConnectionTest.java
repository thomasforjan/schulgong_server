import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseConnectionTest {

  private Connection connection;

  /**
   * Before each test, setup database-configuration.
   *
   * @throws SQLException if method fails
   */
  @BeforeAll
  public void setup() throws SQLException {
    String url = "jdbc:mariadb://10.11.12.13:3306/schulgong";
    String username = "root";
    String password = "avegljgGXJj7";
    connection = DriverManager.getConnection(url, username, password);
  }

  /**
   * After all tests, close connection to database.
   *
   * @throws SQLException if method fails
   */
  @AfterAll
  public void close() throws SQLException {
    connection.close();
  }

  /**
   * Test if the connection to the database can be established.
   *
   * @throws SQLException if method fails
   */
  @Test
  public void testDatabaseConnection() throws SQLException {
    assertNotNull(connection);
  }
}
