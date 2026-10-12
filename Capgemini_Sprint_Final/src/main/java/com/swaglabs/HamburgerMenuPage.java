
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

    public HamburgerMenuPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItems;

    @FindBy(id = "about_sidebar_link")
    private WebElement about;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logout;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetApp;

    private boolean isMenuOpen() {
        return !driver.findElements(
            By.id("logout_sidebar_link")
        ).isEmpty()
        && driver.findElement(
            By.id("logout_sidebar_link")
        ).isDisplayed();
    }

    public void openMenu() {
        if (!isMenuOpen()) {
            wait.until(
                ExpectedConditions.elementToBeClickable(menuButton)
            ).click();

            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.id("logout_sidebar_link")
                )
            );
        }

        Reporter.log("Hamburger menu opened", true);
    }

    public void closeMenu() {
        if (isMenuOpen()) {
            wait.until(
                ExpectedConditions.elementToBeClickable(closeMenuButton)
            ).click();

            wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                    By.id("logout_sidebar_link")
                )
            );
        }

        Reporter.log("Hamburger menu closed", true);
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
            ExpectedConditions.urlContains("inventory.html")
        );

        Reporter.log("All Items selected", true);
    }

    public void clickAbout() {
        openMenu();

        wait.until(
            ExpectedConditions.elementToBeClickable(about)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("saucelabs.com")
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

        Reporter.log("Logout successful", true);
    }

    public void clickResetApp() {
        openMenu();

        wait.until(
            ExpectedConditions.elementToBeClickable(resetApp)
        ).click();

        // SauceDemo normally stays on the inventory page.
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("react-burger-menu-btn")
            )
        );

        Reporter.log("Reset App State selected", true);
    }
}
