package com.swaglabs;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class CheckoutPage2 {

    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage2(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "finish")
    WebElement finishButton;

    public void clickFinish() {
        wait.until(
            ExpectedConditions.elementToBeClickable(finishButton)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("checkout-complete")
        );

        Reporter.log("Finish button clicked successfully", true);
    }
}