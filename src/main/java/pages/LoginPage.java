package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page object for login functionality. */
public class LoginPage extends BasePage {

  private By emailInput = By.name("emailField");
  private By passwordInput = By.name("passField");
  private By signInButton = By.xpath("//*[text()='Sign in']");

  /**
   * Constructs LoginPage with WebDriver.
   *
   * @param driver WebDriver instance
   */
  public LoginPage(WebDriver driver) {
    super(driver);
  }

  /**
   * Logs in using provided email and password.
   *
   * @param email User email
   * @param password User password
   */
  public void login(String email, String password) {
    webDriverHelper.sendKeys(emailInput, "Email Field", email);
    webDriverHelper.sendKeys(passwordInput, "Password Field", password);
    webDriverHelper.click(signInButton, "Sign In Button");
  }
}
