package ecom.swaglabs.product;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.LoginPage;
import com.swaglabs.ProductPage;

import baseclass.BaseClass;

public class Product_Test_Script1 extends BaseClass {

    @Test(groups = {"smoke", "functional"})
    public void verifyAddProductToCart() {

        Reporter.log("Starting Add Product to Cart Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        Reporter.log("Login successful", true);

        ProductPage product = new ProductPage(driver);
        product.addBackpack();

        Assert.assertTrue(
            driver.getPageSource().contains("Remove"),
            "Product was not added to Cart"
        );

        Reporter.log(
            "PASS: Product was added to the cart successfully",
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifyRemoveProduct() {

        Reporter.log("Starting Remove Product Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();

        Reporter.log("Product added to cart", true);

        product.removeBackpack();

        Assert.assertTrue(
            driver.getPageSource().contains("Add to cart"),
            "Product was not removed"
        );

        Reporter.log(
            "PASS: Product was removed successfully",
            true
        );
    }

    @Test(groups = {"smoke", "functional"})
    public void verifyProductPageLoads() {

        Reporter.log("Starting Product Page Load Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);

        Assert.assertTrue(
            product.isProductPageDisplayed(),
            "Product page did not load"
        );

        Reporter.log(
            "PASS: Product inventory page loaded successfully",
            true
        );
    }
}