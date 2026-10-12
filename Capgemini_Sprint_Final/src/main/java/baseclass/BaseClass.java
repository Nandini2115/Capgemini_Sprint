package baseclass;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.JavascriptExecutor;
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
            switch (browser.toLowerCase()) {

                case "chrome": {
                    ChromeOptions options = new ChromeOptions();

                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");

                    options.setExperimentalOption(
                        "prefs",
                        Map.of(
                            "credentials_enable_service", false,
                            "profile.password_manager_enabled", false,
                            "profile.password_manager_leak_detection", false
                        )
                    );

                    driver = new ChromeDriver(options);
                    break;
                }

                case "edge": {
                    System.setProperty(
                        "webdriver.edge.driver",
                        "C:\\Users\\HP\\Downloads\\edgedriver_win64\\msedgedriver.exe"
                    );

                    EdgeOptions options = new EdgeOptions();

                    options.addArguments("--headless=new");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--no-first-run");
                    options.addArguments("--no-default-browser-check");

                    driver = new EdgeDriver(options);
                    break;
                }

                case "firefox": {
                    FirefoxOptions options = new FirefoxOptions();

                    options.addArguments("-headless");
                    options.addArguments("--width=1920");
                    options.addArguments("--height=1080");

                    driver = new FirefoxDriver(options);
                    break;
                }

                default:
                    throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                    );
            }

            driver.manage().timeouts()
                  .implicitlyWait(Duration.ZERO);

            wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
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
                } catch (Exception ignored) {
                    // Ignore cleanup errors
                }
            }

            driver = null;
            wait = null;

            throw e;
        }
    }

    public void waitForPageLoad() {
        wait.until(webDriver ->
            "complete".equals(
                ((JavascriptExecutor) webDriver)
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
                    Screenshot.takeScreenshot(
                        driver,
                        testName + "_FAILED"
                    );

                    Reporter.log(
                        "Test failed: " + testName,
                        true
                    );
                }

            } else if (result.getStatus() == ITestResult.SKIP) {
                System.out.println("SKIP: " + testName);
            }

        } catch (Exception e) {
            System.err.println(
                "Error during test cleanup: " + e.getMessage()
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