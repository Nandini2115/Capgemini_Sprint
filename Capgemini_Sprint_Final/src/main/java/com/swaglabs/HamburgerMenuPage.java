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

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItems;

    @FindBy(id = "about_sidebar_link")
    private WebElement about;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logout;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetApp;

    public HamburgerMenuPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is null in HamburgerMenuPage"
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        PageFactory.initElements(driver, this);
    }

    public void openMenu() {
        wait.until(
            ExpectedConditions.elementToBeClickable(menuButton)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOf(allItems)
        );

        Reporter.log("Hamburger menu opened successfully", true);
    }

    public boolean isAllItemsDisplayed() {
        openMenu();
        return wait.until(
            ExpectedConditions.visibilityOf(allItems)
        ).isDisplayed();
    }

    public boolean isAboutDisplayed() {
        openMenu();
        return wait.until(
            ExpectedConditions.visibilityOf(about)
        ).isDisplayed();
    }

    public boolean isLogoutDisplayed() {
        openMenu();
        return wait.until(
            ExpectedConditions.visibilityOf(logout)
        ).isDisplayed();
    }

    public boolean isResetAppDisplayed() {
        openMenu();
        return wait.until(
            ExpectedConditions.visibilityOf(resetApp)
        ).isDisplayed();
    }

    public void clickAllItems() {
        openMenu();

        wait.until(
            ExpectedConditions.elementToBeClickable(allItems)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("inventory")
        );

        Reporter.log("All Items selected", true);
    }

    public void clickAbout() {
        openMenu();

        wait.until(
            ExpectedConditions.elementToBeClickable(about)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("saucelabs")
        );

        Reporter.log("About selected", true);
    }

    public void clickLogout() {
        openMenu();

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
        openMenu();

        wait.until(
            ExpectedConditions.elementToBeClickable(resetApp)
        ).click();

        Reporter.log("Reset App State selected", true);
    }
}