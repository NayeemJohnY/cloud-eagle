
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

/**
 * Test class for verifying login functionality on the Login Page.
 */
public class LoginPageTest extends BaseTest {

  /**
   * Tests that a user can successfully log in with valid credentials.
   * <p>
   * This test retrieves the email and password from the properties file,
   * performs the login action, and verifies that the user is redirected
   * to the dashboard page and is recognized as logged in.
   * </p>
   */
  @Test
  public void testShouldAbleToLoginWithValidCredentials() {
    String email = readProperties.getProperty("email");
    String password = readProperties.getProperty("password");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(email, password);
    DashboardPage dashboardPage = new DashboardPage(driver);
    Assert.assertTrue(dashboardPage.isUserLoggedInToDashboard(email), "User Login Failure");
  }
}
