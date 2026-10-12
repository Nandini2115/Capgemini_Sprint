package com.swaglabs;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class CheckoutPage3 {

    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage3(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "complete-header")
    WebElement orderSuccessMessage;

    public String getOrderSuccessMessage() {
        String message = wait.until(
            ExpectedConditions.visibilityOf(orderSuccessMessage)
        ).getText();

        Reporter.log("Order confirmation message: " + message, true);

        return message;
    }

    public boolean isOrderPlaced() {
        boolean orderPlaced = getOrderSuccessMessage()
            .equalsIgnoreCase("Thank you for your order!");

        Reporter.log("Order placed status: " + orderPlaced, true);

        return orderPlaced;
    }
}