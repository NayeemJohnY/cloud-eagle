package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

  private By emailInput = By.name("emailField");
  private By passwordInput = By.name("passField");
  private By signInButton = By.xpath("//*[text()='Sign in']");

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  public void login(String email, String password) {
    webDriverHelper.sendKeys(emailInput, "Email Field", email);
    webDriverHelper.sendKeys(passwordInput, "Password Field", password);
    webDriverHelper.click(signInButton, "Sign In Button");
  }
}
