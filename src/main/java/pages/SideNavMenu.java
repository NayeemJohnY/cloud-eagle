package pages;

import base.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page object for side navigation menu interactions. */
public class SideNavMenu extends BasePage {

  private Locator sideNavMenu =
      Locator.xpath(
          "//*[contains(@class, 'apphome_menuWrapper')][following-sibling::label[text()='%s']]");
  private By profileUsername = By.xpath("//p[contains(@class, 'userName')]");

  /**
   * Constructs SideNavMenu with WebDriver.
   *
   * @param driver WebDriver instance
   */
  public SideNavMenu(WebDriver driver) {
    super(driver);
  }

  /**
   * Navigates to a menu item from the side navigation.
   *
   * @param menuName Name of the menu to navigate to
   */
  public void navigateToMenuFromSideNav(String menuName) {
    webDriverHelper.click(sideNavMenu, "Side Nav Menu", "Applications");
    webDriverHelper.moveToElement(profileUsername, menuName);
  }
}
