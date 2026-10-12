package ecom.swaglabs.checkout;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.CartPage;
import com.swaglabs.CheckoutPage1;
import com.swaglabs.LoginPage;
import com.swaglabs.ProductPage;

import baseclass.BaseClass;
import baseclass.Screenshot;

public class CheckOut_Test_Script2 extends BaseClass {

    @Test(groups = {"negative"})
    public void verifyCheckoutWithBlankInformation() throws Exception {

        Reporter.log("Starting Negative Checkout Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        Reporter.log("Login successful", true);

        ProductPage product = new ProductPage(driver);
        product.addBackpack();

        Reporter.log("Product added to cart", true);

        product.clickCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        Reporter.log("Checkout Information page opened", true);

        CheckoutPage1 checkout1 = new CheckoutPage1(driver);
        checkout1.continueWithoutEnteringDetails();

        String errorMessage = checkout1.getErrorMessage();

        Reporter.log(
            "Checkout Error Message: " + errorMessage,
            true
        );

        Assert.assertTrue(
            errorMessage.contains("First Name is required"),
            "First Name required error was not displayed"
        );

        Screenshot.takeScreenshot(
            driver,
            "Checkout_Blank_Information"
        );

        Reporter.log(
            "PASS: Required-field validation displayed correctly",
            true
        );

        Reporter.log(
            "Screenshot saved: Checkout_Blank_Information",
            true
        );
    }
}