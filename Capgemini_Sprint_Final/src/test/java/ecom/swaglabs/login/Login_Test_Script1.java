package ecom.swaglabs.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.LoginPage;

import baseclass.BaseClass;

public class Login_Test_Script1 extends BaseClass {

    @Test(groups = {"smoke", "functional"})
    public void validLoginTest() {

        Reporter.log("Starting Valid Login Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "User was not logged in successfully"
        );

        Reporter.log(
            "PASS: User logged in successfully",
            true
        );
    }

    @Test(groups = {"functional"})
    public void validProblemUserLoginTest() {

        Reporter.log("Starting Problem User Login Test", true);

        LoginPage login = new LoginPage(driver);
        login.problemUserLogin();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Problem user was not logged in successfully"
        );

        Reporter.log(
            "PASS: Problem user login completed",
            true
        );
    }

    @Test(groups = {"functional", "negative"})
    public void invalidLoginTest() {

        Reporter.log("Starting Invalid Login Test", true);

        LoginPage login = new LoginPage(driver);
        login.invalidLogin();

        String errorMessage = login.getErrorMessage();

        Assert.assertTrue(
            errorMessage.length() > 0,
            "Error message was not displayed"
        );

        Reporter.log(
            "PASS: Invalid login error displayed: " + errorMessage,
            true
        );
    }
}