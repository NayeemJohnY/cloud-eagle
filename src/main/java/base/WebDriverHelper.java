package base;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverHelper {

  private static final Logger logger = LogManager.getLogger(WebDriverHelper.class);
  private WebDriver driver;
  private WebDriverWait wait;

  public WebDriverHelper(WebDriver driver, long timeoutInSeconds) {
    this.driver = driver;
    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeoutInSeconds));
  }

  public WebElement waitForElementToVisible(By locator, String elementName) {
    logger.info("Waiting for Element to visible '{}'", elementName);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public WebElement waitForElementToVisible(
      Locator locator, String elementName, Object... replaceValue) {
    logger.info("Waiting for Element to visible '{}'", elementName);
    return wait.until(
        ExpectedConditions.visibilityOfElementLocated(locator.resolveBy(replaceValue)));
  }

  public boolean isElementVisible(By locator, String elementName) {
    try {
      logger.info("Checking if Element is visible '{}'", elementName);
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      return true;
    } catch (Exception e) {
      logger.info("Element '{}' is not visible", elementName);
      return false;
    }
  }

  public boolean isElementVisible(Locator locator, String elementName, Object... replaceValues) {
    By byLocator = locator.resolveBy(replaceValues);
    try {
      logger.info("Checking if Element is visible '{}'", elementName);
      wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
      return true;
    } catch (Exception e) {
      logger.info("Element '{}' is not visible", elementName);
      return false;
    }
  }

  public void sendKeys(By locator, String elementName, String keysToSend) {
    String value = keysToSend;
    if (elementName.toLowerCase().contains("password")) {
      value = "******";
    }
    logger.info("Send Keys '{}' Element '{}'", value, elementName);
    WebElement element = waitForElementToVisible(locator, elementName);
    element.sendKeys(keysToSend);
  }

  public void click(By locator, String elementName) {
    logger.info("Clicking on Element '{}'", elementName);
    WebElement element = waitForElementToVisible(locator, elementName);
    element.click();
  }

  public String getElementText(By locator, String elementName) {
    logger.info("Getting text from Element '{}'", elementName);
    WebElement element = waitForElementToVisible(locator, elementName);
    return element.getText();
  }
}
