package ecom.swaglabs.checkout;

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

public class CheckOut_Test_Script1 extends BaseClass {

    @Test(groups = {"functional"})
    public void verifyCheckoutInformationPage() {

        Reporter.log("Starting Checkout Information Page Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("checkout-step-one"),
            "Checkout Information page was not displayed"
        );

        Reporter.log(
            "PASS: Checkout Information page displayed successfully",
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifyCustomerInformationSubmission() {

        Reporter.log("Starting Customer Information Submission Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage1 checkout1 = new CheckoutPage1(driver);
        checkout1.enterDetails();
        checkout1.clickContinue();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("checkout-step-two"),
            "Checkout Overview page was not displayed"
        );

        Reporter.log(
            "PASS: Customer information submitted successfully",
            true
        );
    }

    @Test(groups = {"integration"})
    public void verifyProductConsistencyInCheckout() {

        Reporter.log("Starting Product Consistency Integration Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);
        String cartProduct = cart.getProductName();

        cart.clickCheckout();

        CheckoutPage1 checkout1 = new CheckoutPage1(driver);
        checkout1.enterDetails();
        checkout1.clickContinue();

        Assert.assertTrue(
            driver.getPageSource().contains(cartProduct),
            "Cart product is not displayed in Checkout Overview"
        );

        Reporter.log(
            "PASS: Product consistency verified in checkout: "
                + cartProduct,
            true
        );
    }

    @Test(groups = {"system"})
    public void verifyCompleteCheckout() {

        Reporter.log("Starting Complete Checkout System Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);
        cart.clickCheckout();

        CheckoutPage1 checkout1 = new CheckoutPage1(driver);
        checkout1.enterDetails();
        checkout1.clickContinue();

        CheckoutPage2 checkout2 = new CheckoutPage2(driver);
        checkout2.clickFinish();

        CheckoutPage3 checkout3 = new CheckoutPage3(driver);

        Assert.assertTrue(
            checkout3.isOrderPlaced(),
            "Order confirmation was not displayed"
        );

        Reporter.log(
            "PASS: Order placed and confirmation displayed successfully",
            true
        );
    }
}