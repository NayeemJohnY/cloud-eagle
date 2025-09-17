package base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ReadProperties;

public class WebDriverManager {
  private static WebDriver driver;
  private static final Logger logger = LogManager.getLogger(WebDriverManager.class);
  private static ReadProperties readProperties = ReadProperties.getInstance();

  private static List<String> getBrowserArguments(String browser, boolean headless) {
    List<String> arguments = new ArrayList<>();

    if (headless) {
      if (browser.equals("firefox")) {
        arguments.add("--headless");
      } else {
        arguments.add("--headless=new");
      }
      arguments.add("--disable-gpu");
      arguments.add("--no-sandbox");
      arguments.add("--disable-dev-shm-usage");
      arguments.add("--window-size=1920,1080");
    } else {
      arguments.add("--start-maximized");
    }
    return arguments;
  }

  private static AbstractDriverOptions<?> getOptions(String browser, boolean headless) {
    List<String> arguments = getBrowserArguments(browser, headless);
    switch (browser) {
      case "chrome":
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(arguments);
        return chromeOptions;

      case "firefox":
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments(arguments);
        return firefoxOptions;

      case "edge":
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments(arguments);
        return edgeOptions;

      default:
        throw new InvalidArgumentException("Invalid browser: " + browser);
    }
  }

  public static WebDriver getDriver() {
    String gridUrl = System.getProperty("grid.url", readProperties.getProperty("grid.url"));
    String browser =
        System.getProperty("browser", readProperties.getProperty("browser")).toLowerCase();
    boolean headless =
        Boolean.parseBoolean(
            System.getProperty("headless", readProperties.getProperty("headless")).toLowerCase());

    logger.info("Set up browser {} with headless {}, grid URL: {}", browser, headless, gridUrl);

    AbstractDriverOptions<?> options = getOptions(browser, headless);
    try {
      if (gridUrl != null && !gridUrl.isEmpty()) {
        // Use Selenium Grid Remote WebDriver
        URL hubUrl = URI.create(gridUrl).toURL();
        driver = new RemoteWebDriver(hubUrl, options);
        logger.info("Connected to Selenium Grid at: {}", gridUrl);
      } else {
        // Use local WebDriver
        switch (browser) {
          case "chrome":
            driver = new ChromeDriver((ChromeOptions) options);
            break;

          case "firefox":
            driver = new FirefoxDriver((FirefoxOptions) options);
            break;

          case "edge":
            driver = new EdgeDriver((EdgeOptions) options);
            break;

          default:
            throw new InvalidArgumentException("Invalid browser: " + browser);
        }
        logger.info("Using local WebDriver");
      }
    } catch (MalformedURLException | IllegalArgumentException e) {
      throw new InvalidArgumentException("Invalid grid URL: " + gridUrl, e);
    }

    return driver;
  }
}
