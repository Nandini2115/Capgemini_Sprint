
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
        Reporter.log("Test Suite Started", true);
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        Reporter.log("Test Started", true);
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        Reporter.log("Test Class Started", true);
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {

        Reporter.log("Browser selected: " + browser, true);

        try {

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();

                Map<String, Object> preferences = new HashMap<>();
                preferences.put("profile.password_manager_leak_detection",false);

                options.setExperimentalOption("prefs", preferences);
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                driver = new ChromeDriver(options);

            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();

                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                driver = new EdgeDriver(options);

            } else if (browser.equalsIgnoreCase("firefox")) {

                FirefoxOptions options = new FirefoxOptions();

                options.addArguments("-headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");

                driver = new FirefoxDriver(options);

            } else {

                throw new IllegalArgumentException("Invalid browser: " + browser);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ZERO);

            wait = new WebDriverWait(driver,Duration.ofSeconds(15));

            driver.get(CommonData.get("url"));

            waitForPageLoad();

            Reporter.log("Application opened successfully",true);

        } catch (RuntimeException e) {

            if (driver != null) {
                try {
                    driver.quit();
                } 
                catch (Exception ignored) {
                }

                driver = null;
                wait = null;
            }

            throw e;
        }
    }

    protected void waitForPageLoad() {
        wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean waitForUrlContains(String value) {
        return wait.until(ExpectedConditions.urlContains(value));
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void logReport(String message) {
        Reporter.log(message, true);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.SUCCESS) {

            Reporter.log("PASS: " + testName, true);

        } else if (result.getStatus() == ITestResult.FAILURE) {

            Reporter.log("FAIL: " + testName, true);

            if (driver != null) {
                try {
                    Screenshot.takeScreenshot(driver,testName + "_FAILED");
                } 
                catch (Exception e) {
                    Reporter.log("Screenshot failed: " + e.getMessage(),true);
                }
            }

        } else if (result.getStatus() == ITestResult.SKIP) {

            Reporter.log("SKIP: " + testName, true);
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                Reporter.log("Browser cleanup failed: " + e.getMessage(),true);
            } finally {
                driver = null;
                wait = null;
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        Reporter.log("Test Class Completed", true);
    }
}
