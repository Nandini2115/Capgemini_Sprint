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

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    WebElement username;

    @FindBy(id = "password")
    WebElement password;

    @FindBy(id = "login-button")
    WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    WebElement errorMessage;

    public void login() {
        wait.until(
            ExpectedConditions.visibilityOf(username)
        ).sendKeys(CommonData.get("username"));

        Reporter.log("Username entered", true);

        wait.until(
            ExpectedConditions.visibilityOf(password)
        ).sendKeys(CommonData.get("password"));

        Reporter.log("Password entered", true);

        wait.until(
            ExpectedConditions.elementToBeClickable(loginButton)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("inventory_list")
            )
        );

        Reporter.log("Login successful", true);
    }

    public void login(String usernameValue, String passwordValue) {
        wait.until(
            ExpectedConditions.visibilityOf(username)
        ).sendKeys(usernameValue);

        Reporter.log("Username entered: " + usernameValue, true);

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
        String message = wait.until(
            ExpectedConditions.visibilityOf(errorMessage)
        ).getText();

        Reporter.log("Login error message: " + message, true);

        return message;
    }
}