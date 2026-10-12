package ecom.swaglabs.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.LoginPage;

import baseclass.BaseClass;
import baseclass.Screenshot;

public class Login_Test_Script2 extends BaseClass {

    @Test(groups = {"negative"})
    public void invalidUsernameTest() {

        Reporter.log("Starting Invalid Username Test", true);

        LoginPage login = new LoginPage(driver);
        login.login("invalid_user", "secret_sauce");

        String errorMessage = login.getErrorMessage();

        Reporter.log(
            "Invalid Username Error: " + errorMessage,
            true
        );

        try {
            Screenshot.takeScreenshot(
                driver,
                "Invalid_Username"
            );

            Reporter.log(
                "Screenshot saved: Invalid_Username",
                true
            );
        } catch (Exception e) {
            Reporter.log(
                "Screenshot could not be saved: " + e.getMessage(),
                true
            );
        }

        Assert.assertTrue(
            errorMessage.length() > 0,
            "Error message was not displayed"
        );

        Reporter.log(
            "PASS: Invalid username validation displayed",
            true
        );
    }

    @Test(groups = {"negative"})
    public void invalidPasswordTest() {

        Reporter.log("Starting Invalid Password Test", true);

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "wrong_password");

        String errorMessage = login.getErrorMessage();

        Reporter.log(
            "Invalid Password Error: " + errorMessage,
            true
        );

        try {
            Screenshot.takeScreenshot(
                driver,
                "Invalid_Password"
            );

            Reporter.log(
                "Screenshot saved: Invalid_Password",
                true
            );
        } catch (Exception e) {
            Reporter.log(
                "Screenshot could not be saved: " + e.getMessage(),
                true
            );
        }

        Assert.assertTrue(
            errorMessage.length() > 0,
            "Error message was not displayed"
        );

        Reporter.log(
            "PASS: Invalid password validation displayed",
            true
        );
    }

    @Test(groups = {"negative"})
    public void emptyCredentialsTest() {

        Reporter.log("Starting Empty Credentials Test", true);

        LoginPage login = new LoginPage(driver);
        login.login("", "");

        String errorMessage = login.getErrorMessage();

        Reporter.log(
            "Empty Credentials Error: " + errorMessage,
            true
        );

        try {
            Screenshot.takeScreenshot(
                driver,
                "Empty_Credentials"
            );

            Reporter.log(
                "Screenshot saved: Empty_Credentials",
                true
            );
        } catch (Exception e) {
            Reporter.log(
                "Screenshot could not be saved: " + e.getMessage(),
                true
            );
        }

        Assert.assertTrue(
            errorMessage.length() > 0,
            "Validation message was not displayed"
        );

        Reporter.log(
            "PASS: Empty credentials validation displayed",
            true
        );
    }
}