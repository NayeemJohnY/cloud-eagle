# Cloud Eagle

This is a Java Selenium project for automated web testing using Selenium WebDriver, TestNG, and Allure reporting.

## Prerequisites

- Java JDK 17 or higher
- Maven 3.6 or higher
- A web browser (e.g., Chrome) with corresponding WebDriver (handled automatically by WebDriverManager)
- This project uses Allure for test reporting. [Official Docs](https://allurereport.org/docs/).

## Setup

1. Clone the repository:
   ```
   git clone <repository-url>
   cd cloud-eagle
   ```

2. Install dependencies:
   ```
   mvn clean install
   ```

## Execution

To run all tests:
```
mvn test
```

To run a specific test class (e.g., LoginPageTest):
```
mvn test -Dtest=LoginPageTest
```

To run tests:
```
mvn clean test
```
To Serve & Generate Allure Report


```
# Serve Allure Report on Server
allure serve test-results/allure-results 
```
OR

```
# Generate Static Allure Report without History
allure generate test-results/allure-results --clean -o test-results/allure-report
```
OR 

```
# Generate Static Allure Report with History
# Step 1: Save history from old report to the results folder
cp -r test-results/allure-report/history test-results/allure-results/history 

# Step 2: Remove the old report folder
rm -r test-results/allure-report

# Step 3: Generate a fresh report with a clean results folder
allure generate test-results/allure-results --clean -o test-results/allure-report
```

## Project Structure

```
cloud-eagle/                                  # Root directory of the project
│
├── src/                                      # Source code directory
│   ├── main/                                 # Main application source
│   │   ├── java/                             # Java source files
│   │   │   ├── base/                         # Base classes for WebDriver and locators
│   │   │   │   ├── Locator.java              # Locator constants or utilities
│   │   │   │   ├── WebDriverHelper.java      # Helper methods for WebDriver
│   │   │   │   └── WebDriverManager.java     # WebDriver setup and management
│   │   │   │
│   │   │   ├── pages/                        # Page Object Model (POM) classes
│   │   │   │   ├── ApplicationsPage.java     # Page class for Applications page
│   │   │   │   ├── BasePage.java             # Base page class with shared methods
│   │   │   │   ├── DashboardPage.java        # Page class for Dashboard page
│   │   │   │   ├── LoginPage.java            # Page class for Login functionality
│   │   │   │   └── SideNavMenu.java          # Page class for side navigation menu
│   │   │   │
│   │   │   └── utils/                        # Utility classes
│   │   │       └── ReadProperties.java       # Utility for reading properties files
│   │   │ 
│   │   └── resources/                        # Resource files (non-Java)
│   │       ├── log4j2.xml                    # Logging configuration
│   │       ├── prod-config.properties        # Production environment properties
│   │       └── sandbox-config.properties     # Sandbox environment properties
│   │
│   └── test/                                 # Test source code
│       └── java/                             # Java test files
│           ├── tests/                        # Test classes
│           │   ├── BaseTest.java             # Base test class with setup/teardown
│           │   ├── DashboardPageTest.java    # Tests for Dashboard page
│           │   └── LoginPageTest.java        # Tests for Login functionality
│           └── testUtils/                    # Test utility classes (if any)
│
├── pom.xml                                   # Maven project configuration file
├── README.md                                 # Project documentation
├── selenium-grid-maven-docker-compose.yml    # Docker Compose for Selenium Grid
├── testng.xml                                # TestNG test suite configuration
└── test-results/                             # Test result outputs (screenshots, logs, etc.)
```


## Configuration

- `prod-config.properties` and `sandbox-config.properties`: Environment-specific configurations
- `log4j2.xml`: Logging configuration
