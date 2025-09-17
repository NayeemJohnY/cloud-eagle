package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ApplicationsPage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SideNavMenu;

public class DashboardPageTest extends BaseTest {

  @Test
  public void testVerifyManagedApplicationsCountShownInDashboard() {
    // Step 1: Login
    String email = readProperties.getProperty("email");
    String password = readProperties.getProperty("password");
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(email, password);
    DashboardPage dashboardPage = new DashboardPage(driver);
    Assert.assertTrue(dashboardPage.isUserLoggedInToDashboard(email), "User Login Failure");

    // Step 2: Get Application Count in Dashboard
    int countFromDashboard = dashboardPage.getCountTextOfCard("Managed Applications");

    // Step 3: Navigate to Applications Menu
    SideNavMenu sideNavMenu = new SideNavMenu(driver);
    sideNavMenu.navigateToMenuFromSideNav("Applications");

    // Step 4: Get Number of Applications in Applications Page
    ApplicationsPage applicationsPage = new ApplicationsPage(driver);
    applicationsPage.waitForApplicationsPageToLoad();
    int countFromApplicationPage = applicationsPage.getApplicationsRowCount();

    // Step 5: Compare Counts
    Assert.assertEquals(countFromApplicationPage, countFromDashboard, "Applications Count");
  }
}
