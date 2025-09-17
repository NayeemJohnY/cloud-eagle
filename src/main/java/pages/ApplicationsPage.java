package pages;

import base.Locator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ApplicationsPage extends BasePage {

  public ApplicationsPage(WebDriver driver) {
    super(driver);
  }

  private By applicationsHeader =
      By.xpath("//*[contains(@class, 'menuHeading')]/descendant::*[text()='Applications']");
  private By cardContainer = By.xpath("//*[contains(@class, 'cardContainer')]");
  private By tableContainer = By.xpath("//*[contains(@class, 'tableContainer')]");
  private Locator tableRowWithDataKey =
      Locator.xpath("//*[@data-key > '%s'][descendant::*[@role='row']]");

  public void waitForApplicationsPageToLoad() {
    webDriverHelper.waitForElementToVisible(applicationsHeader, "Applications Header");
    webDriverHelper.waitForElementToVisible(cardContainer, "Card Container");
    webDriverHelper.waitForElementToVisible(tableContainer, "Table Container");
  }

  public int getApplicationsRowCount() {
    int totalRows = 0;
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
    return totalRows;
  }
}
