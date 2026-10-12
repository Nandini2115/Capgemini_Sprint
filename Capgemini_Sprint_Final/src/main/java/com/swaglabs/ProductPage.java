package com.swaglabs;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import baseclass.CommonData;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private String getProductId() {
        String productName = CommonData.get("productName");
        return productName.toLowerCase().replace(" ", "-");
    }

    public void addBackpack() {
        String productId = getProductId();
        By addButton = By.id("add-to-cart-" + productId);

        wait.until(
            ExpectedConditions.elementToBeClickable(addButton)
        ).click();

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("remove-" + productId)
            )
        );

        Reporter.log("Backpack added to cart successfully", true);
    }

    public void removeBackpack() {
        String productId = getProductId();
        By removeButton = By.id("remove-" + productId);

        wait.until(
            ExpectedConditions.elementToBeClickable(removeButton)
        ).click();

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("add-to-cart-" + productId)
            )
        );

        Reporter.log("Backpack removed from cart successfully", true);
    }

    public void clickCart() {
        By cart = By.className("shopping_cart_link");

        wait.until(
            ExpectedConditions.elementToBeClickable(cart)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("cart")
        );

        Reporter.log("Shopping cart opened successfully", true);
    }

    public boolean isProductPageDisplayed() {
        boolean displayed = wait.until(
            ExpectedConditions.urlContains("inventory")
        );

        Reporter.log("Product inventory page displayed: " + displayed, true);

        return displayed;
    }
}