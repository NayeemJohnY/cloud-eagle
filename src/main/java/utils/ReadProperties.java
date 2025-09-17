package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadProperties {

  private static ReadProperties readPropertiesInstance;
  private final Properties prop;
  private static final Logger logger = LogManager.getLogger(ReadProperties.class);

  private ReadProperties() {
    this.prop = new Properties();
    String env =
        System.getenv("env") != null
            ? System.getenv("env").toLowerCase()
            : System.getProperty("env", "sandbox").toLowerCase();
    String propertiesFileName = env + "-config.properties";
    try (InputStream inputStream =
        ReadProperties.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (inputStream == null) {
        logger.error("Properties file with name '{}' not found", propertiesFileName);
      }
      prop.load(inputStream);
    } catch (IOException e) {
      logger.error(
          "IOException occurred while reading Properties from File {}", propertiesFileName, e);
    }
  }

  public static synchronized ReadProperties getInstance() {
    if (readPropertiesInstance == null) {
      readPropertiesInstance = new ReadProperties();
    }
    return readPropertiesInstance;
  }

  public String getProperty(String key) {
    return prop.getProperty(key).trim();
  }
}
