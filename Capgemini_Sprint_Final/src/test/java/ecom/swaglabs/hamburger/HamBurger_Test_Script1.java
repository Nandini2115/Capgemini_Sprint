package ecom.swaglabs.hamburger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import baseclass.BaseClass;
import com.swaglabs.HamburgerMenuPage;
import com.swaglabs.LoginPage;

public class HamBurger_Test_Script1 extends BaseClass {

    private HamburgerMenuPage openHamburgerMenu() {
        LoginPage login = new LoginPage(driver);
        login.login();

        HamburgerMenuPage hamburger =
            new HamburgerMenuPage(driver);

        hamburger.openMenu();
        return hamburger;
    }

    @Test(groups = {"smoke", "functional"})
    public void verifyAllItemsDisplayed() {
        HamburgerMenuPage hamburger = openHamburgerMenu();

        Assert.assertTrue(
            hamburger.isAllItemsDisplayed(),
            "All Items option is not displayed"
        );

        Reporter.log("PASS: All Items option displayed", true);
    }

    @Test(groups = {"functional"})
    public void verifyAboutDisplayed() {
        HamburgerMenuPage hamburger = openHamburgerMenu();

        Assert.assertTrue(
            hamburger.isAboutDisplayed(),
            "About option is not displayed"
        );

        Reporter.log("PASS: About option displayed", true);
    }

    @Test(groups = {"functional"})
    public void verifyLogoutDisplayed() {
        HamburgerMenuPage hamburger = openHamburgerMenu();

        Assert.assertTrue(
            hamburger.isLogoutDisplayed(),
            "Logout option is not displayed"
        );

        Reporter.log("PASS: Logout option displayed", true);
    }

    @Test(groups = {"functional"})
    public void verifyResetAppDisplayed() {
        HamburgerMenuPage hamburger = openHamburgerMenu();

        Assert.assertTrue(
            hamburger.isResetAppDisplayed(),
            "Reset App State option is not displayed"
        );

        Reporter.log(
            "PASS: Reset App State option displayed", true
        );
    }

    @Test(groups = {"integration"})
    public void verifyAllItemsNavigation() {
        HamburgerMenuPage hamburger = openHamburgerMenu();
        hamburger.clickAllItems();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "All Items navigation failed"
        );
    }

    @Test(groups = {"functional"})
    public void verifyLogout() {
        HamburgerMenuPage hamburger = openHamburgerMenu();
        hamburger.clickLogout();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("saucedemo.com"),
            "Logout did not return to the login page"
        );

        Reporter.log("PASS: Logout completed", true);
    }
}
