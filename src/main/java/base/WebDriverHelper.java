package base;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

  public void waitForElementToVisible(By locator, String elementName) {
    logger.info("Waiting for Element to visible '{}'", elementName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public WebElement getElement(By locator, String elementName) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitForElementToVisible(
      Locator locator, String elementName, Object... replaceValues) {
    logger.info(
        "Waiting for Element to visible '{}' with replace values '{}'", elementName, replaceValues);
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator.resolveBy(replaceValues)));
  }

  public WebElement getElement(Locator locator, String elementName, Object... replaceValues) {
    return wait.until(
        ExpectedConditions.visibilityOfElementLocated(locator.resolveBy(replaceValues)));
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
      logger.info(
          "Checking if Element is visible '{}' with replace values '{}'",
          elementName,
          replaceValues);
      wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
      return true;
    } catch (Exception e) {
      logger.info(
          "Element '{}' with replace value '{}' is not visible", elementName, replaceValues);
      return false;
    }
  }

  public void sendKeys(By locator, String elementName, String keysToSend) {
    String value = keysToSend;
    if (elementName.toLowerCase().contains("password")) {
      value = "******";
    }
    logger.info("Send Keys '{}' to Element '{}'", value, elementName);
    WebElement element = getElement(locator, elementName);
    element.sendKeys(keysToSend);
  }

  public void click(By locator, String elementName) {
    logger.info("Clicking on Element '{}'", elementName);
    WebElement element = getElement(locator, elementName);
    element.click();
  }

  public void click(Locator locator, String elementName, Object... replaceValues) {
    logger.info("Clicking on Element '{}' with replace values '{}'", elementName, replaceValues);
    WebElement element = getElement(locator, elementName, replaceValues);
    element.click();
  }

  public void moveToElement(By locator, String elementName) {
    logger.info("Using Actions, Moving on Element '{}'", elementName);
    Actions actions = new Actions(driver);
    actions.moveToElement(getElement(locator, elementName), 0, 0).perform();
  }

  public String getElementText(By locator, String elementName) {
    logger.info("Getting text from Element '{}'", elementName);
    WebElement element = getElement(locator, elementName);
    return element.getText();
  }

  public String getElementText(Locator locator, String elementName, Object... replaceValues) {
    logger.info(
        "Getting text from Element '{}' with replace values '{}'", elementName, replaceValues);
    WebElement element = getElement(locator, elementName, replaceValues);
    return element.getText();
  }

  public List<WebElement> getAllWebElements(
      Locator locator, String elementName, long timeout, Object... replaceValues) {

    WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    logger.info(
        "Getting Number of Elements '{}' with replace values '{}'", elementName, replaceValues);
    By byLocator = locator.resolveBy(replaceValues);
    try {
      List<WebElement> elements =
          customWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byLocator));
      return elements;
    } catch (TimeoutException e) {
      return Collections.emptyList();
    }
  }

  public void scrollIntoView(WebElement element, String elementName) {
    logger.info("Scrolling into Element '{}'", elementName);
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    javascriptExecutor.executeScript("arguments[0].scrollIntoView()", element);
  }
}
