package ecom.swaglabs.hamburger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.HamburgerMenuPage;
import com.swaglabs.LoginPage;

import baseclass.BaseClass;
import baseclass.Screenshot;

public class HamBurger_Test_Script1 extends BaseClass {

    @Test(groups = {"smoke"})
    public void verifyHamburgerMenu() {

        Reporter.log("Starting Hamburger Menu Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        HamburgerMenuPage hamburger = new HamburgerMenuPage(driver);
        hamburger.openMenu();

        Assert.assertTrue(
            hamburger.isDynamicCatalogDisplayed(),
            "Dynamic Catalog is not displayed"
        );

        Reporter.log(
            "PASS: Hamburger menu and Dynamic Catalog displayed",
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifyDynamicCatalogDropdown() {

        Reporter.log("Starting Dynamic Catalog Dropdown Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        HamburgerMenuPage hamburger = new HamburgerMenuPage(driver);
        hamburger.openMenu();
        hamburger.openDynamicCatalog();

        Assert.assertTrue(
            hamburger.isLazyLoadDisplayed(),
            "Lazy Load is not displayed"
        );

        Assert.assertTrue(
            hamburger.isSliderDisplayed(),
            "Slider is not displayed"
        );

        Assert.assertTrue(
            hamburger.isSpinnerDisplayed(),
            "Spinner is not displayed"
        );

        Reporter.log(
            "PASS: Lazy Load, Slider and Spinner are displayed",
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifyLazyLoadClickable() {

        Reporter.log("Starting Lazy Load Click Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        HamburgerMenuPage hamburger = new HamburgerMenuPage(driver);
        hamburger.openMenu();
        hamburger.clickLazyLoad();

        Reporter.log("PASS: Lazy Load selected", true);
    }

    @Test(groups = {"functional"})
    public void verifySliderClickable() {

        Reporter.log("Starting Slider Click Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        HamburgerMenuPage hamburger = new HamburgerMenuPage(driver);
        hamburger.openMenu();
        hamburger.clickSlider();

        Reporter.log("PASS: Slider selected", true);
    }

    @Test(groups = {"functional"})
    public void verifySpinnerClickable() {

        Reporter.log("Starting Spinner Click Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        HamburgerMenuPage hamburger = new HamburgerMenuPage(driver);
        hamburger.openMenu();
        hamburger.clickSpinner();

        Reporter.log("PASS: Spinner selected", true);
    }

    @Test(groups = {"negative"})
    public void verifyUnavailableProductAction() {

        Reporter.log("Starting Unavailable Product Negative Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        String unavailableProduct = "Unavailable Product";

        boolean productFound = driver.getPageSource()
            .contains(unavailableProduct);

        Reporter.log(
            "Unavailable Product Found: " + productFound,
            true
        );

        try {
            Screenshot.takeScreenshot(
                driver,
                "Unavailable_Product_Action"
            );

            Reporter.log(
                "Screenshot saved: Unavailable_Product_Action",
                true
            );
        } catch (Exception e) {
            Reporter.log(
                "Screenshot could not be saved: " + e.getMessage(),
                true
            );
        }

        Assert.assertFalse(
            productFound,
            "Unavailable product should not be displayed"
        );

        Reporter.log(
            "PASS: Unavailable product was not displayed",
            true
        );
    }
}