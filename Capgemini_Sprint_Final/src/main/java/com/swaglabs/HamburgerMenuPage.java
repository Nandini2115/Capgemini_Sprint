package com.swaglabs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    public HamburgerMenuPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void openMenu() {
        if (isMenuOpen()) {
            return;
        }

        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();

        wait.until(ExpectedConditions.visibilityOf(allItems));
        System.out.println("Hamburger menu opened successfully");
    }

    private boolean isMenuOpen() {
        try {
            return allItems.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeMenu() {
        if (isMenuOpen()) {
            wait.until(
                ExpectedConditions.elementToBeClickable(closeMenuButton)
            ).click();

            wait.until(
                ExpectedConditions.invisibilityOf(allItems)
            );
        }
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
        wait.until(ExpectedConditions.elementToBeClickable(allItems)).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public void clickAbout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(about)).click();
    }

    public void clickLogout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            org.openqa.selenium.By.id("login-button")
        ));
        System.out.println("Logout successful");
    }

    public void clickResetApp() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(resetApp)).click();
        System.out.println("Reset App State clicked");
    }
}