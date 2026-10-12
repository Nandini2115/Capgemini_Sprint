package baseclass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {

        driver = null;
        wait = null;

        System.out.println("Browser selected: " + browser);

        try {
            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-first-run");
                options.addArguments("--no-default-browser-check");

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                driver = new ChromeDriver(options);

            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-first-run");
                options.addArguments("--no-default-browser-check");
                options.addArguments("--disable-extensions");

                driver = new EdgeDriver(options);

            } else if (browser.equalsIgnoreCase("firefox")) {

                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");

                driver = new FirefoxDriver(options);

            } else {
                throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
                );
            }

            if (driver == null) {
                throw new IllegalStateException(
                    "WebDriver initialization failed."
                );
            }

            driver.manage().timeouts()
                  .implicitlyWait(Duration.ZERO);

            wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            driver.manage().window().maximize();

            String url = CommonData.get("url");

            if (url == null || url.trim().isEmpty()) {
                throw new IllegalStateException(
                    "URL is missing in CommonData."
                );
            }

            driver.get(url);

            wait.until(d ->
                ((org.openqa.selenium.JavascriptExecutor) d)
                    .executeScript("return document.readyState")
                    .equals("complete")
            );

            System.out.println(
                "DEBUG: WebDriver initialized: "
                + driver.getClass().getName()
            );

            System.out.println("Browser started successfully.");

        } catch (RuntimeException e) {

            System.err.println(
                "Browser setup failed: " + e.getMessage()
            );

            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception ignored) {
                }
            }

            driver = null;
            wait = null;

            throw e;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("PASS: " + testName);

        } else if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("FAIL: " + testName);

            if (driver != null) {
                try {
                    Screenshot.takeScreenshot(
                        driver, testName + "_FAILED"
                    );

                    Reporter.log(
                        "Screenshot captured for " + testName,
                        true
                    );
                } catch (Exception e) {
                    System.err.println(
                        "Screenshot failed: " + e.getMessage()
                    );
                }
            }

        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("SKIP: " + testName);
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println(
                    "Browser cleanup failed: " + e.getMessage()
                );
            }
        }

        driver = null;
        wait = null;
    }
}