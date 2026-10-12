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

        Assert.assertNotNull(
            driver, "WebDriver is null before login."
        );

        Reporter.log("Starting Invalid Username Test", true);

        LoginPage login = new LoginPage(driver);
        login.login("invalid_user", "secret_sauce");

        assertErrorDisplayed(login);

        captureScreenshot("Invalid_Username");
    }

    @Test(groups = {"negative"})
    public void invalidPasswordTest() {

        Assert.assertNotNull(
            driver, "WebDriver is null before login."
        );

        Reporter.log("Starting Invalid Password Test", true);

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "wrong_password");

        assertErrorDisplayed(login);

        captureScreenshot("Invalid_Password");
    }

    @Test(groups = {"negative"})
    public void emptyCredentialsTest() {

        Assert.assertNotNull(
            driver, "WebDriver is null before login."
        );

        Reporter.log("Starting Empty Credentials Test", true);

        LoginPage login = new LoginPage(driver);
        login.login("", "");

        assertErrorDisplayed(login);

        captureScreenshot("Empty_Credentials");
    }

    private void assertErrorDisplayed(LoginPage login) {

        String errorMessage = login.getErrorMessage();

        Assert.assertFalse(
            errorMessage.isEmpty(),
            "Validation message was not displayed"
        );

        Reporter.log(
            "Validation message: " + errorMessage, true
        );
    }

    private void captureScreenshot(String name) {

        try {
            Screenshot.takeScreenshot(driver, name);

            Reporter.log(
                "Screenshot saved: " + name, true
            );
        } catch (Exception e) {
            Reporter.log(
                "Screenshot could not be saved: "
                    + e.getMessage(),
                true
            );
        }
    }
}