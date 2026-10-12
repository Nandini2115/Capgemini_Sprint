package ecom.swaglabs.cart;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.CartPage;
import com.swaglabs.CheckoutPage1;
import com.swaglabs.CheckoutPage2;
import com.swaglabs.CheckoutPage3;
import com.swaglabs.LoginPage;
import com.swaglabs.ProductPage;

import baseclass.BaseClass;

public class Cart_Test_Script3 extends BaseClass {

    @Test(groups = {"integration", "system"})
    public void verifyCompleteCheckout() {

        Reporter.log("Starting Complete Checkout Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        Reporter.log("Step 1: Login completed", true);

        ProductPage product = new ProductPage(driver);
        product.addBackpack();

        Reporter.log("Step 2: Product added to cart", true);

        product.clickCart();

        Reporter.log("Step 3: Shopping cart opened", true);

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        Reporter.log("Step 4: Checkout page opened", true);

        CheckoutPage1 checkout1 = new CheckoutPage1(driver);
        checkout1.enterDetails();
        checkout1.clickContinue();

        Reporter.log("Step 5: Checkout information submitted", true);

        CheckoutPage2 checkout2 = new CheckoutPage2(driver);
        checkout2.clickFinish();

        Reporter.log("Step 6: Finish button clicked", true);

        CheckoutPage3 checkout3 = new CheckoutPage3(driver);

        Assert.assertTrue(
            checkout3.isOrderPlaced(),
            "Order was not placed successfully"
        );

        Reporter.log(
            "PASS: Complete checkout flow finished successfully",
            true
        );
    }
}