
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

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public CheckoutPage1(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver cannot be null"
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(15)
        );

        PageFactory.initElements(driver, this);
    }

    // Enter checkout customer information
    public void enterDetails() {
        enterValue(
            firstNameField,
            CommonData.get("firstName")
        );

        enterValue(
            lastNameField,
            CommonData.get("lastName")
        );

        enterValue(
            postalCodeField,
            CommonData.get("postalCode")
        );

        Reporter.log(
            "Checkout details entered successfully", true
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

    // Submit checkout information and handle either outcome
    public void clickContinue() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                continueButton
            )
        ).click();

        wait.until(d ->
            d.getCurrentUrl().contains("checkout-step-two.html")
            || !d.findElements(
                By.cssSelector("[data-test='error']")
            ).isEmpty()
        );

        if (driver.getCurrentUrl().contains(
                "checkout-step-two.html")) {

            Reporter.log(
                "Checkout information submitted successfully",
                true
            );

        } else {
            String message = driver.findElement(
                By.cssSelector("[data-test='error']")
            ).getText();

            throw new AssertionError(
                "Checkout validation failed: " + message
            );
        }
    }

    // Negative test: click Continue with empty fields
    public void continueWithoutEnteringDetails() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                continueButton
            )
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")
            )
        );

        Reporter.log(
            "Checkout validation message displayed",
            true
        );
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(
            By.cssSelector("[data-test='error']")
        ).isEmpty();
    }

    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")
            )
        ).getText();
    }

    public boolean isCheckoutOverviewDisplayed() {
        return driver.getCurrentUrl().contains(
            "checkout-step-two.html"
        );
    }
}
