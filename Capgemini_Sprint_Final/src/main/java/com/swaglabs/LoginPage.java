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

import baseclass.CommonData;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "user-name")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is null. Ensure setUp() runs first."
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(15)
        );

        PageFactory.initElements(driver, this);
    }

    public void login() {
        login(
            CommonData.get("username"),
            CommonData.get("password")
        );

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("inventory_list")
            )
        );

        Reporter.log("Login successful", true);
    }

    public void login(
            String usernameValue,
            String passwordValue) {

        wait.until(
            ExpectedConditions.visibilityOf(username)
        ).sendKeys(usernameValue);

        Reporter.log("Username entered", true);

        wait.until(
            ExpectedConditions.visibilityOf(password)
        ).sendKeys(passwordValue);

        Reporter.log("Password entered", true);

        wait.until(
            ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

        Reporter.log("Login button clicked", true);
    }

    public void invalidLogin() {
        login(
            CommonData.get("invalidUsername"),
            CommonData.get("invalidPassword")
        );
    }

    public void problemUserLogin() {
        login(
            CommonData.get("problemUsername"),
            CommonData.get("password")
        );
    }

    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOf(errorMessage)
        ).getText();
    }
}