package ecom.swaglabs.cart;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.CartPage;
import com.swaglabs.LoginPage;
import com.swaglabs.ProductPage;

import baseclass.BaseClass;
import baseclass.CommonData;

public class Cart_Test_Script1 extends BaseClass {

    @Test(groups = {"smoke", "functional"})
    public void verifyProductInCart() {

        Reporter.log("Starting verifyProductInCart test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);

        String actualProductName = cart.getProductName();
        String expectedProductName = CommonData.get("productName");

        Assert.assertEquals(
            actualProductName,
            expectedProductName,
            "Incorrect product displayed in cart"
        );

        Reporter.log(
            "PASS: Expected product is displayed in the cart: "
                + actualProductName,
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifyRemoveProductFromCart() {

        Reporter.log("Starting verifyRemoveProductFromCart test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        ProductPage product = new ProductPage(driver);
        product.addBackpack();
        product.clickCart();

        CartPage cart = new CartPage(driver);
        cart.removeProduct();

        Assert.assertTrue(
            cart.isCartEmpty(),
            "Product was not removed from cart"
        );

        Reporter.log(
            "PASS: Product was successfully removed from the cart",
            true
        );
    }
}