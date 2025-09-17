package base;

import org.openqa.selenium.By;

public class Locator {

  private String locatorType;
  private String locatorValue;

  private Locator(String locatorType, String locatorValue) {
    this.locatorType = locatorType;
    this.locatorValue = locatorValue;
  }

  public static Locator xpath(String xpathExpression) {
    return new Locator("xpath", xpathExpression);
  }

  public static Locator css(String cssSelector) {
    return new Locator("css", cssSelector);
  }

  public By resolveBy(Object... replaceValues) {
    String replacedLocatorValue = String.format(locatorValue, replaceValues);
    return switch (locatorType) {
      case "xpath" -> By.xpath(replacedLocatorValue);
      case "css" -> By.cssSelector(replacedLocatorValue);
      default -> throw new IllegalArgumentException("UnSupported Locator Type " + locatorType);
    };
  }
}
