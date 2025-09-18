package pages;

import base.Locator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Page object for applications functionality. */
public class ApplicationsPage extends BasePage {

  /**
   * Constructs ApplicationsPage with WebDriver.
   *
   * @param driver WebDriver instance
   */
  public ApplicationsPage(WebDriver driver) {
    super(driver);
  }

  private By applicationsHeader =
      By.xpath("//*[contains(@class, 'menuHeading')]/descendant::*[text()='Applications']");
  private By cardContainer = By.xpath("//*[contains(@class, 'cardContainer')]");
  private By tableContainer = By.xpath("//*[contains(@class, 'tableContainer')]");
  private Locator tableRowWithDataKey =
      Locator.xpath("//*[@data-key > '%s'][descendant::*[@role='row']]");
  private By tableNextPageIcon =
      By.xpath(
          "//button[contains(@class, 'footerButton')][last()][not(contains(@class, 'disabled'))]");

  /** Waits for applications page elements to load. */
  public void waitForApplicationsPageToLoad() {
    webDriverHelper.waitForElementToVisible(applicationsHeader, "Applications Header");
    webDriverHelper.waitForElementToVisible(cardContainer, "Card Container");
    webDriverHelper.waitForElementToVisible(tableContainer, "Table Container");
  }

  /**
   * Returns the total number of rows in the applications table, including all paginated pages.
   *
   * <p>Iterates through each page, collects all rows, and clicks the next page icon until no more
   * pages are available. Uses the last row's data-key to fetch subsequent rows and scrolls into
   * view for each last row.
   *
   * @return Total number of application rows across all pages
   */
  public int getApplicationsRowCount() {
    int totalRows = 0;
    while (true) {
      String lastRowDataKey = "-1";
      while (true) {
        List<WebElement> rows =
            webDriverHelper.getAllWebElements(
                tableRowWithDataKey, "Table Rows with Data Key", 10, lastRowDataKey);
        if (rows.isEmpty()) {
          break;
        }
        totalRows += rows.size();

        WebElement lastRow = rows.get(rows.size() - 1);
        lastRowDataKey = lastRow.getAttribute("data-key");

        webDriverHelper.scrollIntoView(lastRow, "Last Visible Row");
      }

      if (!webDriverHelper.isElementVisible(tableNextPageIcon, "Enabled Table Next Page Icon", 5)) {
        break;
      }

      webDriverHelper.click(tableNextPageIcon, "Enabled Table Next Page Icon");
    }
    return totalRows;
  }
}
