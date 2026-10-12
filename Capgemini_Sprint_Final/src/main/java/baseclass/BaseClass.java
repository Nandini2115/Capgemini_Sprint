
package baseclass;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Test Suite Started");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Test Started");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Test Class Started");
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {

        System.out.println("Browser selected: " + browser);

        try {
            switch (browser.toLowerCase().trim()) {

                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");

                    chromeOptions.setExperimentalOption(
                        "prefs",
                        java.util.Map.of(
                            "credentials_enable_service", false,
                            "profile.password_manager_enabled", false,
                            "profile.password_manager_leak_detection", false
                        )
                    );

                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();

                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    edgeOptions.addArguments("--no-first-run");
                    edgeOptions.addArguments("--no-default-browser-check");

                    // Dedicated profile for Jenkins automation.
                    edgeOptions.addArguments(
                        "--user-data-dir=C:\\JenkinsEdgeProfile"
                    );

                    driver = new EdgeDriver(edgeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");

                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                default:
                    throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                    );
            }

            driver.manage().timeouts()
                  .implicitlyWait(Duration.ZERO);

            wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
            );

            driver.get(CommonData.get("url"));
            waitForPageLoad();

            System.out.println("Application opened successfully");

        } catch (RuntimeException e) {
            System.err.println(
                "Browser setup failed for " + browser + ": "
                + e.getMessage()
            );

            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception cleanupError) {
                    System.err.println(
                        "Browser cleanup failed: "
                        + cleanupError.getMessage()
                    );
                }
            }

            driver = null;
            wait = null;

            throw e;
        }
    }

    protected void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(webDriver ->
                "complete".equals(
                    ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                )
            );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        String testName =
            result.getMethod().getMethodName();

        try {
            if (result.getStatus() == ITestResult.SUCCESS) {
                System.out.println("PASS: " + testName);

            } else if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("FAIL: " + testName);

                if (driver != null) {
                    try {
                        Screenshot.takeScreenshot(
                            driver, testName + "_FAILED"
                        );
                    } catch (Exception screenshotError) {
                        System.err.println(
                            "Screenshot failed: "
                            + screenshotError.getMessage()
                        );
                    }
                }

            } else if (result.getStatus() == ITestResult.SKIP) {
                System.out.println("SKIP: " + testName);
            }

            Reporter.log(
                result.getStatus() == ITestResult.SUCCESS
                    ? "PASS: " + testName
                    : result.getStatus() == ITestResult.FAILURE
                        ? "FAIL: " + testName
                        : "SKIP: " + testName,
                true
            );

        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    System.err.println(
                        "Error closing browser: " + e.getMessage()
                    );
                }
            }

            driver = null;
            wait = null;
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        System.out.println("Test Class Completed");
    }
}
