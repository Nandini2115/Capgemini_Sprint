package com.swaglabs;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import baseclass.CommonData;

public class CheckoutPage1 {

    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage1(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "first-name")
    WebElement firstName;

    @FindBy(id = "last-name")
    WebElement lastName;

    @FindBy(id = "postal-code")
    WebElement postalCode;

    @FindBy(id = "continue")
    WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    WebElement errorMessage;

    public void enterDetails() {
        wait.until(
            ExpectedConditions.visibilityOf(firstName)
        ).sendKeys(CommonData.get("firstName"));

        Reporter.log("First name entered", true);

        wait.until(
            ExpectedConditions.visibilityOf(lastName)
        ).sendKeys(CommonData.get("lastName"));

        Reporter.log("Last name entered", true);

        wait.until(
            ExpectedConditions.visibilityOf(postalCode)
        ).sendKeys(CommonData.get("postalCode"));

        Reporter.log("Postal code entered", true);
    }

    public void clickContinue() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("checkout-step-two")
        );

        Reporter.log("Checkout information submitted successfully", true);
    }

    public void continueWithoutEnteringDetails() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOf(errorMessage)
        );

        Reporter.log("Checkout validation message displayed", true);
    }

    public String getErrorMessage() {
        String message = wait.until(
            ExpectedConditions.visibilityOf(errorMessage)
        ).getText();

        Reporter.log("Checkout error message: " + message, true);

        return message;
    }
}