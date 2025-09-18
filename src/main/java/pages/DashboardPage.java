package pages;

import base.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page object for dashboard functionality. */
public class DashboardPage extends BasePage {

  private By dashboardHeader = By.xpath("//h2[text()='Dashboard']");
  private Locator cardTitleLocator =
      Locator.xpath("//p[contains(@class, 'cardTitle') and text()='%s']");
  private Locator profileUsername =
      Locator.xpath("//p[contains(@class, 'userName') and text()='%s']");
  private Locator cardCountText =
      Locator.xpath(
          "//*[contains(@class, 'cardDetail')]"
              + "[descendant::*[text()='%s']]/descendant::*[contains(@class, 'countText')]");

  /**
   * Constructs DashboardPage with WebDriver.
   *
   * @param driver WebDriver instance
   */
  public DashboardPage(WebDriver driver) {
    super(driver);
  }

  /**
   * Checks if user is logged in to dashboard.
   *
   * @param email User email
   * @return true if dashboard is visible for user
   */
  public boolean isUserLoggedInToDashboard(String email) {
    webDriverHelper.waitForElementToVisible(cardTitleLocator, "Card Title", "Managed Applications");
    webDriverHelper.waitForElementToVisible(profileUsername, "Profile Username", email);
    return webDriverHelper.isElementVisible(dashboardHeader, "Dashboard Header");
  }

  /**
   * Gets count text of a dashboard card.
   *
   * @param cardTitle Title of the card
   * @return Card count as integer
   */
  public int getCountTextOfCard(String cardTitle) {
    String countText =
        webDriverHelper.getElementText(cardCountText, "Card Count Text", "Managed Applications");
    return Integer.parseInt(countText);
  }

  /**
   * Navigates to a card menu by title.
   *
   * @param cardTitle Title of the card
   */
  public void navigateToCardMenu(String cardTitle) {
    webDriverHelper.click(cardTitleLocator, "Card Title", cardTitle);
  }
}
