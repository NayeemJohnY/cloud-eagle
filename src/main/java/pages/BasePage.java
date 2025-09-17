package pages;

import base.WebDriverHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.ReadProperties;

public class BasePage {

  protected final WebDriver driver;
  protected final WebDriverHelper webDriverHelper;
  protected static final Logger logger = LogManager.getLogger();
    protected ReadProperties readProperties = ReadProperties.getInstance();


  public BasePage(WebDriver driver) {
    this.driver = driver;
    long timeoutInSeconds = Long.parseLong(readProperties.getProperty("timeoutInSeconds"));
    this.webDriverHelper = new WebDriverHelper(driver, timeoutInSeconds);
  }

}
