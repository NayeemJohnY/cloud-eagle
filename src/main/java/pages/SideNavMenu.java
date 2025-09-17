package pages;

import base.Locator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SideNavMenu extends BasePage {

  private Locator sideNavMenu =
      Locator.xpath(
          "//*[contains(@class, 'apphome_menuWrapper')][following-sibling::label[text()='%s']]");
  private By profileUsername = By.xpath("//p[contains(@class, 'userName')]");

  public SideNavMenu(WebDriver driver) {
    super(driver);
  }

  public void navigateToMenuFromSideNav(String menuName) {
    webDriverHelper.click(sideNavMenu, "Side Nav Menu", "Applications");
    webDriverHelper.moveToElement(profileUsername, menuName);
  }
}
