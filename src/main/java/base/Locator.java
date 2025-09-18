package base;

import org.openqa.selenium.By;

/**
 * Locator - Dynamic element locator with parameter substitution.
 *
 * <p>Supports XPath and CSS selectors with String.format() parameter substitution.
 *
 *
 * <h3>Features:</h3>
 *
 * <ul>
 *   <li>XPath and CSS selector support
 *   <li>Parameter substitution with String.format()
 *   <li>Type-safe locator creation
 * </ul>
 *
 * <h3>Usage:</h3>
 *
 * <pre>{@code
 * Locator userRow = Locator.xpath("//table//tr/td[text()='%s']");
 * By specificUserRow = userRow.resolveBy("John Doe");
 * }</pre>
 *
 */
public class Locator {

  /** The type of locator (xpath, css) */
  private String locatorType;

  /** The locator expression with optional format placeholders */
  private String locatorValue;

  /**
   * Private constructor for creating Locator instances.
   *
   * <p>This constructor is private to enforce the use of factory methods for creating Locator
   * instances.
   *
   * @param locatorType The type of locator (xpath, css)
   * @param locatorValue The locator expression that may contain format placeholders
   */
  private Locator(String locatorType, String locatorValue) {
    this.locatorType = locatorType;
    this.locatorValue = locatorValue;
  }

  /**
   * Creates XPath-based Locator with parameter substitution support.
   *
   * @param xpathExpression XPath expression with format placeholders (%s, %d, etc.)
   * @return New Locator instance for XPath
   */
  public static Locator xpath(String xpathExpression) {
    return new Locator("xpath", xpathExpression);
  }

  /**
   * Creates CSS selector-based Locator with parameter substitution support.
   *
   * @param cssSelector CSS selector with format placeholders (%s, %d, etc.)
   * @return New Locator instance for CSS selectors
   */
  public static Locator css(String cssSelector) {
    return new Locator("css", cssSelector);
  }

  /**
   * Resolves locator by substituting format placeholders with actual values.
   *
   * <p>Uses String.format() to substitute placeholders (%s, %d, %f, etc.) with provided values.
   *
   * @param replaceValues Values to substitute into the locator expression
   * @return Selenium By locator with substituted values
   * @throws IllegalArgumentException if locator type is unsupported
   * @throws IllegalFormatException if parameter substitution fails
   */
  public By resolveBy(Object... replaceValues) {
    String replacedLocatorValue = String.format(locatorValue, replaceValues);
    return switch (locatorType) {
      case "xpath" -> By.xpath(replacedLocatorValue);
      case "css" -> By.cssSelector(replacedLocatorValue);
      default -> throw new IllegalArgumentException("UnSupported Locator Type " + locatorType);
    };
  }
}
