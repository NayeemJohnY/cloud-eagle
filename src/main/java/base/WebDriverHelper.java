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

/**
 * WebDriverHelper - Utility class for robust web element interactions.
 *
 * <p>Provides enhanced methods for element waiting, interaction, and visibility checks. Supports
 * both standard By locators and custom Locator objects with parameter substitution.
 *
 *
 * <h3>Usage:</h3>
 *
 * <pre>{@code
 * WebDriverHelper helper = new WebDriverHelper(driver, 10);
 * helper.click(By.id("login-button"), "Login Button");
 * }</pre>
 */
public class WebDriverHelper {

  /** Logger instance for this class */
  private static final Logger logger = LogManager.getLogger(WebDriverHelper.class);

  /** WebDriver instance for browser interactions */
  private WebDriver driver;

  /** WebDriverWait instance with configured timeout */
  private WebDriverWait wait;

  /**
   * Constructs WebDriverHelper with specified timeout.
   *
   * @param driver WebDriver instance for browser interactions
   * @param timeoutInSeconds timeout in seconds for element waiting
   */
  public WebDriverHelper(WebDriver driver, long timeoutInSeconds) {
    this.driver = driver;
    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeoutInSeconds));
  }

  /**
   * Waits for element to become visible.
   *
   * @param locator By locator to find the element
   * @param elementName descriptive name for logging
   */
  public void waitForElementToVisible(By locator, String elementName) {
    logger.info("Waiting for Element to visible '{}'", elementName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Gets a visible WebElement using standard By locator.
   *
   * <p>This method waits for the element to become visible and then returns the WebElement. It's a
   * convenience method that combines waiting and element retrieval.
   *
   * @param locator The By locator strategy to find the element
   * @param elementName Descriptive name of the element for logging purposes
   * @return The visible WebElement ready for interaction
   * @throws TimeoutException if the element is not visible within the configured timeout
   */
  public WebElement getElement(By locator, String elementName) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Waits for element to become visible using a custom Locator and parameter substitution.
   * @param locator Custom Locator object
   * @param elementName Descriptive name for logging
   * @param replaceValues Values to substitute in locator
   */
  public void waitForElementToVisible(
    Locator locator, String elementName, Object... replaceValues) {
    logger.info(
        "Waiting for Element to visible '{}' with replace values '{}'", elementName, replaceValues);
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator.resolveBy(replaceValues)));
  }

  /**
   * Gets a visible WebElement using a custom Locator and parameter substitution.
   * @param locator Custom Locator object
   * @param elementName Descriptive name for logging
   * @param replaceValues Values to substitute in locator
   * @return The visible WebElement
   */
  public WebElement getElement(Locator locator, String elementName, Object... replaceValues) {
    return wait.until(
        ExpectedConditions.visibilityOfElementLocated(locator.resolveBy(replaceValues)));
  }

  /**
   * Checks if an element is visible on the page.
   *
   * <p>This method attempts to wait for the element to become visible within the configured timeout
   * period. Returns true if the element becomes visible, false otherwise.
   *
   * @param locator The By locator strategy to find the element
   * @param elementName Descriptive name of the element for logging purposes
   * @return true if the element is visible, false otherwise
   */
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

  /**
   * Checks if an element is visible with a custom timeout.
   * @param locator By locator
   * @param elementName Descriptive name for logging
   * @param timeout Timeout in seconds
   * @return true if visible, false otherwise
   */
  public boolean isElementVisible(By locator, String elementName, long timeout) {
    WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    try {
      logger.info("Checking if Element is visible '{}'", elementName);
      customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      return true;
    } catch (Exception e) {
      logger.info("Element '{}' is not visible", elementName);
      return false;
    }
  }

  /**
   * Checks if an element is visible using a custom Locator and parameter substitution.
   * @param locator Custom Locator object
   * @param elementName Descriptive name for logging
   * @param replaceValues Values to substitute in locator
   * @return true if visible, false otherwise
   */
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

  /**
   * Sends keystrokes to an input element.
   *
   * <p>This method waits for the element to be visible, clears any existing content, and then sends
   * the specified keystrokes. For password fields, the input is masked in logs for security.
   *
   * @param locator The By locator strategy to find the input element
   * @param elementName Descriptive name of the element for logging purposes
   * @param keysToSend The keystrokes to send to the element
   * @throws TimeoutException if the element is not visible within the configured timeout
   */
  public void sendKeys(By locator, String elementName, String keysToSend) {
    String value = keysToSend;
    if (elementName.toLowerCase().contains("password")) {
      value = "******";
    }
    logger.info("Send Keys '{}' to Element '{}'", value, elementName);
    WebElement element = getElement(locator, elementName);
    element.sendKeys(keysToSend);
  }

  /**
   * Clicks on a clickable element.
   *
   * <p>This method waits for the element to be visible and then performs a click action. It handles
   * various clickable elements including buttons, links, and other interactive elements.
   *
   * @param locator The By locator strategy to find the clickable element
   * @param elementName Descriptive name of the element for logging purposes
   * @throws TimeoutException if the element is not visible within the configured timeout
   */
  public void click(By locator, String elementName) {
    logger.info("Clicking on Element '{}'", elementName);
    WebElement element = getElement(locator, elementName);
    element.click();
  }

  /**
   * Clicks on an element using a custom Locator and parameter substitution.
   * @param locator Custom Locator object
   * @param elementName Descriptive name for logging
   * @param replaceValues Values to substitute in locator
   */
  public void click(Locator locator, String elementName, Object... replaceValues) {
    logger.info("Clicking on Element '{}' with replace values '{}'", elementName, replaceValues);
    WebElement element = getElement(locator, elementName, replaceValues);
    element.click();
  }

  /**
   * Moves mouse to an element using Actions.
   * @param locator By locator
   * @param elementName Descriptive name for logging
   */
  public void moveToElement(By locator, String elementName) {
    logger.info("[Actions] Moving on Element '{}'", elementName);
    Actions actions = new Actions(driver);
    actions.moveToElement(getElement(locator, elementName), 0, 0).perform();
  }

  /**
   * Gets the visible text content of an element.
   *
   * <p>This method waits for the element to be visible and then retrieves its text content. The
   * text returned is the visible text as rendered by the browser.
   *
   * @param locator The By locator strategy to find the element
   * @param elementName Descriptive name of the element for logging purposes
   * @return The visible text content of the element
   * @throws TimeoutException if the element is not visible within the configured timeout
   */
  public String getElementText(By locator, String elementName) {
    logger.info("Getting text from Element '{}'", elementName);
    WebElement element = getElement(locator, elementName);
    return element.getText();
  }

  /**
   * Gets visible text from an element using a custom Locator and parameter substitution.
   * @param locator Custom Locator object
   * @param elementName Descriptive name for logging
   * @param replaceValues Values to substitute in locator
   * @return Visible text content
   */
  public String getElementText(Locator locator, String elementName, Object... replaceValues) {
    logger.info(
        "Getting text from Element '{}' with replace values '{}'", elementName, replaceValues);
    WebElement element = getElement(locator, elementName, replaceValues);
    return element.getText();
  }

  /**
   * Gets all visible WebElements matching a custom Locator and parameter substitution, with custom timeout.
   * @param locator Custom Locator object
   * @param elementName Descriptive name for logging
   * @param timeout Timeout in seconds
   * @param replaceValues Values to substitute in locator
   * @return List of visible WebElements, or empty if none found
   */
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

  /**
   * Scrolls an element into the visible area of the browser window.
   *
   * <p>This method uses JavaScript to scroll the specified element into view, making it visible
   * within the current viewport. This is useful for elements that are below the fold or outside the
   * current visible area.
   *
   * @param element The WebElement to scroll into view
   * @param elementName Descriptive name of the element for logging purposes
   */
  public void scrollIntoView(WebElement element, String elementName) {
    logger.info("Scrolling into Element '{}'", elementName);
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    javascriptExecutor.executeScript("arguments[0].scrollIntoView()", element);
  }
}
