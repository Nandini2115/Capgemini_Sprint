
package com.swaglabs;

import java.time.Duration;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import baseclass.CommonData;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(
                "WebDriver cannot be null"
            );
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(15)
        );
    }

    private String getProductId() {
        String productName = CommonData.get("productName");

        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "productName is missing in CommonData.properties"
            );
        }

        return productName.trim()
            .toLowerCase(Locale.ROOT)
            .replaceAll("\\s+", "-");
    }

    public void addBackpack() {
        String productId = getProductId();

        By addButton = By.id("add-to-cart-" + productId);
        By removeButton = By.id("remove-" + productId);

        wait.until(
            ExpectedConditions.elementToBeClickable(addButton)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(removeButton)
        );

        Reporter.log(
            productId + " added to cart successfully", true
        );
    }

    public void removeBackpack() {
        String productId = getProductId();

        By removeButton = By.id("remove-" + productId);
        By addButton = By.id("add-to-cart-" + productId);

        wait.until(
            ExpectedConditions.elementToBeClickable(removeButton)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(addButton)
        );

        Reporter.log(
            productId + " removed from cart successfully", true
        );
    }

    public void clickCart() {
        By cart = By.className("shopping_cart_link");

        wait.until(
            ExpectedConditions.elementToBeClickable(cart)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("cart.html")
        );

        Reporter.log("Shopping cart opened successfully", true);
    }

    public boolean isProductPageDisplayed() {
        boolean displayed = wait.until(
            ExpectedConditions.urlContains("inventory.html")
        );

        Reporter.log(
            "Product inventory page displayed: " + displayed, true
        );

        return displayed;
    }
}
