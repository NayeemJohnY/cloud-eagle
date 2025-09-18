package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton utility class for reading environment-specific properties files.
 * <p>
 * Loads properties from a file named "{env}-config.properties" where "env" is determined
 * by the "env" environment variable or system property (defaults to "sandbox").
 * Provides thread-safe access to property values.
 * </p>
 *
 * Usage:
 * <pre>
 *   ReadProperties props = ReadProperties.getInstance();
 *   String value = props.getProperty("some.key");
 * </pre>
 */
public class ReadProperties {

  private static ReadProperties readPropertiesInstance;
  private final Properties prop;
  private static final Logger logger = LogManager.getLogger(ReadProperties.class);

  
  /**
   * Private constructor for the ReadProperties class.
   * Initializes the Properties object and loads environment-specific configuration
   * from a properties file based on the "env" environment variable or system property.
   * Logs an error if the properties file is not found or if an IOException occurs during loading.
   */
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


  /**
   * Returns the singleton instance of {@code ReadProperties}.
   * <p>
   * This method is thread-safe and ensures that only one instance of
   * {@code ReadProperties} is created throughout the application's lifecycle.
   *
   * @return the singleton {@code ReadProperties} instance
   */
  public static synchronized ReadProperties getInstance() {
    if (readPropertiesInstance == null) {
      readPropertiesInstance = new ReadProperties();
    }
    return readPropertiesInstance;
  }


  /**
   * Retrieves the value associated with the specified key from the properties.
   * Trims any leading or trailing whitespace from the value before returning.
   * Returns {@code null} if the key does not exist.
   *
   * @param key the property key to look up
   * @return the trimmed property value, or {@code null} if not found
   */
  public String getProperty(String key) {
    return prop.getProperty(key) == null ? null: prop.getProperty(key).trim();
  }
}
