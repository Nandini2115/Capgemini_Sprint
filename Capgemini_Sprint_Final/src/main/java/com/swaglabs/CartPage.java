
package com.swaglabs;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    public CartPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        PageFactory.initElements(driver, this);
    }

    public String getProductName() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".cart_item .inventory_item_name")
            )
        ).getText();
    }

    public void clickCheckout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(checkoutButton)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("checkout-step-one.html")
        );

        Reporter.log("Checkout information page opened", true);
    }

    public void continueShopping() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                continueShoppingButton
            )
        ).click();

        wait.until(
            ExpectedConditions.urlContains("inventory.html")
        );

        Reporter.log("Returned to product inventory", true);
    }

    public boolean isCartDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("cart_contents_container")
            )
        ).isDisplayed();
    }

    public int getCartItemCount() {
        return driver.findElements(By.className("cart_item")).size();
    }

    public boolean isProductInCart(String productName) {
        return !driver.findElements(
            By.xpath(
                "//div[contains(@class,'cart_item')]"
                + "//div[contains(@class,'inventory_item_name')"
                + " and normalize-space()='"
                + productName + "']"
            )
        ).isEmpty();
    }

    // Removes the first product in the cart.
    public void removeProduct() {
        WebElement removeButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(".cart_item button[id^='remove-']")
            )
        );

        removeButton.click();

        Reporter.log("First product removed from cart", true);
    }

    // Removes a specific product by name.
    public void removeProduct(String productName) {
        WebElement removeButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//div[contains(@class,'cart_item')"
                    + "][.//div[contains(@class,'inventory_item_name')"
                    + " and normalize-space()='"
                    + productName + "']]"
                    + "//button[starts-with(@id,'remove-')]"
                )
            )
        );

        removeButton.click();

        Reporter.log("Removed product: " + productName, true);
    }

    public boolean isCartEmpty() {
        return driver.findElements(By.className("cart_item")).isEmpty();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
