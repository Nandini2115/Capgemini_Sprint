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

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "inventory_item_name")
    WebElement productName;

    @FindBy(id = "remove-sauce-labs-backpack")
    WebElement removeProduct;

    @FindBy(id = "checkout")
    WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    WebElement continueShoppingButton;

    public String getProductName() {
        String name = wait.until(
            ExpectedConditions.visibilityOf(productName)
        ).getText();

        Reporter.log("Product in cart: " + name, true);
        return name;
    }

    public void removeProduct() {
        wait.until(
            ExpectedConditions.elementToBeClickable(removeProduct)
        ).click();

        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                By.className("cart_item")
            )
        );

        Reporter.log("Product removed from cart successfully", true);
    }

    public boolean isCartEmpty() {
        boolean empty = wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                By.className("cart_item")
            )
        );

        Reporter.log("Cart empty status: " + empty, true);
        return empty;
    }

    public void clickCheckout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(checkoutButton)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("checkout-step-one")
        );

        Reporter.log("Checkout button clicked successfully", true);
    }

    public void continueShopping() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                continueShoppingButton
            )
        ).click();

        wait.until(
            ExpectedConditions.urlContains("inventory")
        );

        Reporter.log("Returned to product inventory", true);
    }
}