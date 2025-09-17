package tests;

import base.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ReadProperties;

public class BaseTest {

  protected WebDriver driver;
  protected ReadProperties readProperties = ReadProperties.getInstance();
 protected static final Logger logger = LogManager.getLogger();

  @BeforeMethod
  public void setup() {
    driver = WebDriverManager.getDriver();
    String url = readProperties.getProperty("url");
    logger.info("Launching application URL: {}", url);
    driver.get(url);
  }

  @AfterMethod
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
