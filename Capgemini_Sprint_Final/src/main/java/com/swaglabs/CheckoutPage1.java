
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
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    private final By errorLocator =
        By.cssSelector("[data-test='error']");

    public CheckoutPage1(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver cannot be null"
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(20)
        );

        PageFactory.initElements(driver, this);
    }

    // Enter customer details from CommonData.properties
    public void enterDetails() {
        enterValue(firstNameField, CommonData.get("firstName"));
        enterValue(lastNameField, CommonData.get("lastName"));
        enterValue(postalCodeField, CommonData.get("postalCode"));

        Reporter.log(
            "Checkout customer details entered successfully", true
        );
    }

    private void enterValue(WebElement element, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Checkout test data is missing or empty"
            );
        }

        wait.until(
            ExpectedConditions.visibilityOf(element)
        );

        element.clear();
        element.sendKeys(value);
    }

    // Submit customer information
    public void clickContinue() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        try {
            wait.until(d ->
                d.getCurrentUrl().contains("checkout-step-two.html")
                || !d.findElements(errorLocator).isEmpty()
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            throw new AssertionError(
                "Checkout did not advance and no validation error "
                + "was detected."
                + "\nCurrent URL: " + driver.getCurrentUrl()
                + "\nPage title: " + driver.getTitle(),
                e
            );
        }

        if (driver.getCurrentUrl().contains("checkout-step-two.html")) {
            Reporter.log(
                "Checkout information submitted successfully", true
            );
        } else {
            String message = getErrorMessage();

            Reporter.log(
                "Checkout validation error: " + message, true
            );

            throw new AssertionError(
                "Checkout did not proceed: " + message
            );
        }
    }

    // Negative test: submit empty customer information
    public void continueWithoutEnteringDetails() {
        wait.until(
            ExpectedConditions.visibilityOf(firstNameField)
        ).clear();

        lastNameField.clear();
        postalCodeField.clear();

        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        String message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorLocator)
        ).getText();

        Reporter.log(
            "Expected validation message: " + message, true
        );
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(errorLocator).isEmpty();
    }

    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorLocator)
        ).getText();
    }

    public boolean isCheckoutOverviewDisplayed() {
        return driver.getCurrentUrl().contains(
            "checkout-step-two.html"
        );
    }
}
