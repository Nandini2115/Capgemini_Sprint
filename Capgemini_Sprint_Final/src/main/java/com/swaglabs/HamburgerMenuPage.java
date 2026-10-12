package com.swaglabs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class HamburgerMenuPage {

    WebDriver driver;
    WebDriverWait wait;

    public HamburgerMenuPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    WebElement menuButton;

    @FindBy(id = "inventory_sidebar_link")
    WebElement allItems;

    @FindBy(id = "about_sidebar_link")
    WebElement about;

    @FindBy(id = "logout_sidebar_link")
    WebElement logout;

    @FindBy(id = "reset_sidebar_link")
    WebElement resetApp;

    private By dynamicCatalog =
        By.xpath("//*[normalize-space()='Dynamic Catalog']");

    private By lazyLoad =
        By.xpath("//*[normalize-space()='Lazy Load']");

    private By slider =
        By.xpath("//*[normalize-space()='Slider']");

    private By spinner =
        By.xpath("//*[normalize-space()='Spinner']");

    public void openMenu() {
        wait.until(
            ExpectedConditions.elementToBeClickable(menuButton)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(dynamicCatalog)
        );

        Reporter.log("Hamburger menu opened successfully", true);
    }

    public void openDynamicCatalog() {
        if (driver.findElements(lazyLoad).isEmpty()) {
            wait.until(
                ExpectedConditions.elementToBeClickable(dynamicCatalog)
            ).click();
        }

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(lazyLoad)
        );

        Reporter.log("Dynamic Catalog submenu opened", true);
    }

    public boolean isDynamicCatalogDisplayed() {
        boolean displayed = wait.until(
            ExpectedConditions.visibilityOfElementLocated(dynamicCatalog)
        ).isDisplayed();

        Reporter.log(
            "Dynamic Catalog displayed: " + displayed,
            true
        );

        return displayed;
    }

    public boolean isLazyLoadDisplayed() {
        openDynamicCatalog();

        boolean displayed = wait.until(
            ExpectedConditions.visibilityOfElementLocated(lazyLoad)
        ).isDisplayed();

        Reporter.log("Lazy Load displayed: " + displayed, true);

        return displayed;
    }

    public void clickLazyLoad() {
        openDynamicCatalog();

        wait.until(
            ExpectedConditions.elementToBeClickable(lazyLoad)
        ).click();

        Reporter.log("Lazy Load selected", true);
    }

    public boolean isSliderDisplayed() {
        openDynamicCatalog();

        boolean displayed = wait.until(
            ExpectedConditions.visibilityOfElementLocated(slider)
        ).isDisplayed();

        Reporter.log("Slider displayed: " + displayed, true);

        return displayed;
    }

    public void clickSlider() {
        openDynamicCatalog();

        wait.until(
            ExpectedConditions.elementToBeClickable(slider)
        ).click();

        Reporter.log("Slider selected", true);
    }

    public boolean isSpinnerDisplayed() {
        openDynamicCatalog();

        boolean displayed = wait.until(
            ExpectedConditions.visibilityOfElementLocated(spinner)
        ).isDisplayed();

        Reporter.log("Spinner displayed: " + displayed, true);

        return displayed;
    }

    public void clickSpinner() {
        openDynamicCatalog();

        wait.until(
            ExpectedConditions.elementToBeClickable(spinner)
        ).click();

        Reporter.log("Spinner selected", true);
    }

    public void clickAllItems() {
        wait.until(
            ExpectedConditions.elementToBeClickable(allItems)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("inventory")
        );

        Reporter.log("All Items selected", true);
    }

    public void clickAbout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(about)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("saucelabs")
        );

        Reporter.log("About selected", true);
    }

    public void clickLogout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(logout)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("login-button")
            )
        );

        Reporter.log("Logged out successfully", true);
    }

    public void clickResetApp() {
        wait.until(
            ExpectedConditions.elementToBeClickable(resetApp)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("react-burger-menu-btn")
            )
        );

        Reporter.log("Reset App State selected", true);
    }
}