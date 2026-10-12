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

public class CheckoutPage1 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "postal-code")
    private WebElement postalCode;

    @FindBy(id = "continue")
    private WebElement continueButton;

    private final By errorMessage =
        By.cssSelector("[data-test='error']");

    public CheckoutPage1(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver is null in CheckoutPage1"
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        PageFactory.initElements(driver, this);
    }

    public void enterDetails() {
        enterValue(firstName, CommonData.get("firstName"));
        Reporter.log("First name entered", true);

        enterValue(lastName, CommonData.get("lastName"));
        Reporter.log("Last name entered", true);

        enterValue(postalCode, CommonData.get("postalCode"));
        Reporter.log("Postal code entered", true);
    }

    private void enterValue(WebElement element, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(
                "Checkout test data is missing. Check CommonData.properties."
            );
        }

        wait.until(
            ExpectedConditions.visibilityOf(element)
        ).clear();

        element.sendKeys(value);
    }

    public void clickContinue() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        wait.until(d ->
            d.getCurrentUrl().contains("checkout-step-two")
            || !d.findElements(errorMessage).isEmpty()
        );

        if (driver.getCurrentUrl().contains("checkout-step-two")) {
            Reporter.log(
                "Checkout information submitted successfully", true
            );
            return;
        }

        String message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();

        throw new AssertionError(
            "Checkout could not continue. Application message: " + message
        );
    }

    public void continueWithoutEnteringDetails() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        String message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();

        Reporter.log(
            "Checkout validation message: " + message, true
        );
    }

    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }
}