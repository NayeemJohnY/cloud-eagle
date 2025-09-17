package pages;

import base.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

  private By dashboardHeader = By.xpath("//h2[text()='Dashboard']");
  private Locator cardTitle = Locator.xpath("//p[contains(@class, 'cardTitle') and text()='%s']");
  private Locator profileUsername = Locator.xpath("//p[contains(@class, 'userName') and text()='%s']");

  public DashboardPage(WebDriver driver) {
    super(driver);
  }

  public boolean isUserLoggedInToDashboard(String email) {
    webDriverHelper.waitForElementToVisible(cardTitle, "Card Title", "Managed Applications");
    webDriverHelper.waitForElementToVisible(profileUsername, "Profile Username", email);
    return webDriverHelper.isElementVisible(dashboardHeader, "Dashboard Header");
  }
}
