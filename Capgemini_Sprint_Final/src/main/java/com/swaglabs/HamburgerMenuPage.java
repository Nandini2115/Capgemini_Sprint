
package com.swaglabs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class HamburgerMenuPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By menuButton =
            By.id("react-burger-menu-btn");

    private final By closeMenuButton =
            By.id("react-burger-cross-btn");

    private final By allItemsLink =
            By.id("inventory_sidebar_link");

    private final By aboutLink =
            By.id("about_sidebar_link");

    private final By logoutLink =
            By.id("logout_sidebar_link");

    private final By resetAppStateLink =
            By.id("reset_sidebar_link");

    private final By loginButton =
            By.id("login-button");

    public HamburgerMenuPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                    "WebDriver cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));
    }

    public void openMenu() {
        wait.until(
                ExpectedConditions.elementToBeClickable(menuButton))
                .click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutLink));

        Reporter.log("Hamburger menu opened", true);
    }

    public void closeMenu() {
        wait.until(
                ExpectedConditions.elementToBeClickable(closeMenuButton))
                .click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(logoutLink));
    }

    public boolean isAllItemsDisplayed() {
        openMenu();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(allItemsLink))
                .isDisplayed();
    }

    public boolean isAboutDisplayed() {
        openMenu();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(aboutLink))
                .isDisplayed();
    }

    public boolean isLogoutDisplayed() {
        openMenu();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutLink))
                .isDisplayed();
    }

    public boolean isResetAppStateDisplayed() {
        openMenu();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        resetAppStateLink))
                .isDisplayed();
    }

    // Compatibility with the existing TestNG test method name
    public boolean isResetAppDisplayed() {
        return isResetAppStateDisplayed();
    }

    public void clickAllItems() {
        openMenu();

        wait.until(
                ExpectedConditions.elementToBeClickable(allItemsLink))
                .click();

        wait.until(
                ExpectedConditions.urlContains("inventory.html"));

        Reporter.log("All Items selected", true);
    }

    public void clickAbout() {
        openMenu();

        wait.until(
                ExpectedConditions.elementToBeClickable(aboutLink))
                .click();

        Reporter.log("About option selected", true);
    }

    public void clickLogout() {
        openMenu();

        wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink))
                .click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginButton));

        Reporter.log("Logout successful", true);
    }

    public void clickResetAppState() {
        openMenu();

        wait.until(
                ExpectedConditions.elementToBeClickable(resetAppStateLink))
                .click();

        Reporter.log("Reset App State selected", true);
    }
}
