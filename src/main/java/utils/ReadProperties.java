package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadProperties {

  private Properties prop;
  private static Logger logger = LogManager.getLogger(ReadProperties.class);
  
  public ReadProperties() {
    this.prop = new Properties();
    String env =
        System.getenv("env") != null
            ? System.getenv("env").toLowerCase()
            : System.getProperty("env", "sandbox").toLowerCase();
    try (InputStream inputStream =
        ReadProperties.class.getResourceAsStream(env + "-config.properties")) {
      if (inputStream == null) {}

      prop.load(inputStream);
    } catch (IOException e) {
      logger.error("IOException occurred while reading Properties", e);
    }
  }

  public String getProperty(String key) {
    return prop.getProperty(key).trim();
  }
}
