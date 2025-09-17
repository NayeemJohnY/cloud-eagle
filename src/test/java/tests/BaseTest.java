package tests;

import base.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
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
  public void teardown(ITestResult result) {

    if (result.getStatus() != ITestResult.SUCCESS && driver != null) {
      String methodName = result.getMethod().getMethodName();
      String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = methodName + "_" + timestamp + ".png";

      Path destDir = Paths.get("test-results", "screenshots");
      Path destFile = destDir.resolve(filename);

      try {
        Files.createDirectories(destDir);

        TakesScreenshot takeScreenshot = (TakesScreenshot) (driver);
        File tempFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

        Files.copy(tempFile.toPath(), destFile);

        logger.info("Screenshot saved to: " + destFile.toAbsolutePath());
      } catch (IOException e) {
        logger.error("Failed to capture screenshot", e);
      }
    }

    if (driver != null) {
      driver.quit();
    }
  }
}
