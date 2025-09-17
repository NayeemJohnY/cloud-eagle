package base;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ReadProperties;

public class WebDriverManager {
  private static WebDriver driver;
  private static final Logger logger = LogManager.getLogger(WebDriverManager.class);
  private static ReadProperties readProperties = ReadProperties.getInstance();

  private static List<String> getBrowserOptions(String browser, boolean headless) {
    List<String> options = new ArrayList<>();
    options.add("--start-maximized");

    if (headless) {
      if (browser.equals("firefox")) {
        options.add("--headless");
      } else {
        options.add("--headless=new");
      }
      options.add("--disable-gpu");
      options.add("--no-sandbox");
      options.add("--disable-dev-shm-usage");
    }
    return options;
  }

  public static WebDriver getDriver() {
    String browser =
        System.getProperty("browser", readProperties.getProperty("browser")).toLowerCase();
    boolean headless =
        Boolean.parseBoolean(
            System.getProperty("headless", readProperties.getProperty("headless")).toLowerCase());

    logger.info("Set up browser {} with headless {}", browser, headless);

    switch (browser) {
      case "chrome":
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(getBrowserOptions(browser, headless));
        driver = new ChromeDriver(chromeOptions);
        break;

      case "firefox":
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments(getBrowserOptions(browser, headless));
        driver = new FirefoxDriver(firefoxOptions);
        break;

      case "edge":
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments(getBrowserOptions(browser, headless));
        driver = new EdgeDriver(edgeOptions);
        break;

      default:
        throw new InvalidArgumentException("Invalid browser: " + browser);
    }
    return driver;
  }
}
