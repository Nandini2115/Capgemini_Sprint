package ecom.swaglabs.cart;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.CartPage;
import com.swaglabs.LoginPage;
import com.swaglabs.ProductPage;

import baseclass.BaseClass;
import baseclass.CommonData;
import baseclass.Screenshot;

public class Cart_Test_Script2 extends BaseClass {

    @Test(groups = {"integration"})
    public void verifyInventoryToCartIntegration() {

        Reporter.log("Starting Cart Integration Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);

        String cartProduct = cart.getProductName();
        String expectedProduct = CommonData.get("productName");

        Assert.assertEquals(
            cartProduct,
            expectedProduct,
            "Inventory product is not correctly reflected in Cart"
        );

        Reporter.log(
            "PASS: Inventory product is correctly displayed in Cart: "
                + cartProduct,
            true
        );
    }

    @Test(groups = {"integration"})
    public void verifyContinueShopping() {

        Reporter.log("Starting Continue Shopping Integration Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);
        cart.continueShopping();

        wait.until(
            ExpectedConditions.urlContains("inventory")
        );

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Continue Shopping did not return to Inventory page"
        );

        Reporter.log(
            "PASS: Continue Shopping returned to Inventory page",
            true
        );
    }

    @Test(groups = {"negative"})
    public void verifyUnauthenticatedCart() {

        Reporter.log("Starting Unauthenticated Cart Negative Test", true);

        driver.get(CommonData.get("url") + "cart.html");

        wait.until(
            ExpectedConditions.urlContains("saucedemo.com")
        );

        Reporter.log(
            "Current URL after direct cart access: "
                + driver.getCurrentUrl(),
            true
        );

        try {
            Screenshot.takeScreenshot(
                driver,
                "Unauthenticated_Cart"
            );

            Reporter.log(
                "Screenshot saved: Unauthenticated_Cart",
                true
            );
        } catch (Exception e) {
            Reporter.log(
                "Screenshot could not be saved: " + e.getMessage(),
                true
            );
        }

        Assert.assertFalse(
            driver.getCurrentUrl().contains("cart.html"),
            "Unauthenticated user should not access Cart"
        );

        Reporter.log(
            "PASS: Unauthenticated user was prevented from accessing Cart",
            true
        );
    }
}