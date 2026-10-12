package ecom.swaglabs.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.LoginPage;
import baseclass.BaseClass;

public class Login_Test_Script1 extends BaseClass {

    @Test(groups = {"smoke", "functional"})
    public void validLoginTest() {

        Assert.assertNotNull(
            driver, "WebDriver is null before login."
        );

        Reporter.log("Starting Valid Login Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "User was not logged in successfully"
        );

        Reporter.log("PASS: User logged in successfully", true);
    }

    @Test(groups = {"functional"})
    public void validProblemUserLoginTest() {

        Assert.assertNotNull(
            driver, "WebDriver is null before login."
        );

        Reporter.log("Starting Problem User Login Test", true);

        LoginPage login = new LoginPage(driver);
        login.problemUserLogin();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Problem user was not logged in successfully"
        );

        Reporter.log(
            "Problem user login test completed", true
        );
    }

    @Test(groups = {"functional", "negative"})
    public void invalidLoginTest() {

        Assert.assertNotNull(
            driver, "WebDriver is null before login."
        );

        Reporter.log("Starting Invalid Login Test", true);

        LoginPage login = new LoginPage(driver);
        login.invalidLogin();

        String errorMessage = login.getErrorMessage();

        Assert.assertFalse(
            errorMessage.isEmpty(),
            "Error message was not displayed"
        );

        Reporter.log(
            "PASS: Invalid login error displayed: "
                + errorMessage,
            true
        );
    }
}