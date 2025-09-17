package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginPageTest extends BaseTest {

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
