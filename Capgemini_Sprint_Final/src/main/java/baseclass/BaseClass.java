
package baseclass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        logReport("Test Suite Started");
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        logReport("Test Started");
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        logReport("Test Class Started");
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {

        logReport("Browser selected: " + browser);

        try {
            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.password_manager_leak_detection", false);
                options.setExperimentalOption("prefs", prefs);

                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                driver = new ChromeDriver(options);

            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();

                // Run Edge normally first to diagnose the startup crash.
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                driver = new EdgeDriver(options);

            } else if (browser.equalsIgnoreCase("firefox")) {

                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");

                driver = new FirefoxDriver(options);

            } else {
                throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
                );
            }

            driver.manage().timeouts()
                  .implicitlyWait(Duration.ZERO);

            wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            driver.manage().window().maximize();

            driver.get(CommonData.get("url"));

            waitForPageLoad();

            logReport("Application opened successfully");

        } catch (RuntimeException e) {

            logReport("Browser setup failed for " + browser
                    + ": " + e.getMessage());

            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception ignored) {
                    // Cleanup after startup failure.
                }
            }

            driver = null;
            wait = null;

            throw e;
        }
    }

    protected void waitForPageLoad() {
        wait.until(webDriver ->
            ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete")
        );
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(
            ExpectedConditions.elementToBeClickable(locator)
        );
    }

    protected void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }

    protected void waitForInvisibility(By locator) {
        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }

    protected void logReport(String message) {
        Reporter.log(message, true);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            logReport("PASS: " + result.getName());

        } else if (result.getStatus() == ITestResult.FAILURE) {
            logReport("FAIL: " + result.getName());

            if (driver != null) {
                try {
                    Screenshot.takeScreenshot(
                        driver,
                        result.getName() + "_FAILED"
                    );
                } catch (Exception e) {
                    logReport("Screenshot failed: " + e.getMessage());
                }
            }

        } else if (result.getStatus() == ITestResult.SKIP) {
            logReport("SKIP: " + result.getName());
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logReport("Browser cleanup failed: " + e.getMessage());
            }
        }

        driver = null;
        wait = null;
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        logReport("Test Class Completed");
    }
}
