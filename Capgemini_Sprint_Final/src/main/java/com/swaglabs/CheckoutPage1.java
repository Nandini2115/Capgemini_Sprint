
package com.swaglabs;

import baseclass.CommonData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement orderConfirmation;

    public CheckoutPage1(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        PageFactory.initElements(driver, this);
    }

    public void enterDetails() {
        enterValue(firstNameField, CommonData.get("firstName"));
        enterValue(lastNameField, CommonData.get("lastName"));
        enterValue(postalCodeField, CommonData.get("postalCode"));

        System.out.println("Checkout details entered successfully");
    }

    private void enterValue(WebElement element, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Checkout test data is missing or empty"
            );
        }

        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(value);
    }

    public void clickContinue() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();

        try {
            wait.until(d ->
                d.getCurrentUrl().contains("checkout-step-two.html")
                || !d.findElements(
                    By.cssSelector("[data-test='error']")
                ).isEmpty()
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            throw new AssertionError(
                "Checkout did not proceed. Current URL: "
                + driver.getCurrentUrl(), e
            );
        }

        if (driver.getCurrentUrl().contains("checkout-step-two.html")) {
            System.out.println("Checkout information submitted successfully");
        } else {
            String message = driver.findElement(
                By.cssSelector("[data-test='error']")
            ).getText();

            throw new AssertionError(
                "Checkout validation failed: " + message
            );
        }
    }

    public void continueWithoutEnteringDetails() {
        wait.until(
            ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }

    public boolean isErrorDisplayed() {
        return !driver.findElements(
            By.cssSelector("[data-test='error']")
        ).isEmpty();
    }

    public String getErrorMessage() {
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")
            )
        );

        return errorMessage.getText();
    }

    public boolean isCheckoutOverviewDisplayed() {
        return driver.getCurrentUrl().contains("checkout-step-two.html");
    }

    public void clickFinish() {
        wait.until(
            ExpectedConditions.elementToBeClickable(finishButton)
        ).click();

        System.out.println("Finish button clicked successfully");
    }

    public boolean isOrderPlaced() {
        wait.until(
            ExpectedConditions.visibilityOf(orderConfirmation)
        );

        return orderConfirmation.getText()
            .equalsIgnoreCase("Thank you for your order!");
    }
}
