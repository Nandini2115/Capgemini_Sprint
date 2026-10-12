package ecom.swaglabs.product;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.swaglabs.LoginPage;

import baseclass.BaseClass;

public class Product_Test_Script2 extends BaseClass {

    private void sortProducts(String option) {
        By sortLocator = By.className("product_sort_container");

        WebDriverWait productWait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement sortElement = productWait.until(
            ExpectedConditions.elementToBeClickable(sortLocator)
        );

        Select sortDropdown = new Select(sortElement);
        sortDropdown.selectByVisibleText(option);

        productWait.until(
            webDriver -> {
                Select currentDropdown = new Select(
                    webDriver.findElement(sortLocator)
                );
                return currentDropdown.getFirstSelectedOption()
                    .getText().equals(option);
            }
        );

        Reporter.log("Selected sorting option: " + option, true);
    }

    private List<String> getProductNames() {
        List<WebElement> products = driver.findElements(
            By.className("inventory_item_name")
        );

        List<String> names = new ArrayList<>();

        for (WebElement product : products) {
            names.add(product.getText());
        }

        return names;
    }

    private List<Double> getProductPrices() {
        List<WebElement> prices = driver.findElements(
            By.className("inventory_item_price")
        );

        List<Double> values = new ArrayList<>();

        for (WebElement price : prices) {
            String priceText = price.getText().replace("$", "").trim();
            values.add(Double.parseDouble(priceText));
        }

        return values;
    }

    @Test(groups = {"smoke"})
    public void verifySortNameAToZ() {

        Reporter.log("Starting Product Sorting A to Z Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        sortProducts("Name (A to Z)");

        List<String> actualNames = getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);

        Collections.sort(expectedNames);

        Assert.assertEquals(
            actualNames,
            expectedNames,
            "Products are not sorted in A to Z order"
        );

        Reporter.log(
            "PASS: Products are sorted by name A to Z",
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifySortNameZToA() {

        Reporter.log("Starting Product Sorting Z to A Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        sortProducts("Name (Z to A)");

        List<String> actualNames = getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);

        Collections.sort(
            expectedNames,
            Collections.reverseOrder()
        );

        Assert.assertEquals(
            actualNames,
            expectedNames,
            "Products are not sorted in Z to A order"
        );

        Reporter.log(
            "PASS: Products are sorted by name Z to A",
            true
        );
    }

    @Test(groups = {"functional"})
    public void verifyPriceLowToHigh() {

        Reporter.log("Starting Price Low to High Test", true);

        LoginPage login = new LoginPage(driver);
        login.login();

        sortProducts("Price (low to high)");

        List<Double> actualPrices = getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);

        Collections.sort(expectedPrices);

        Assert.assertEquals(
            actualPrices,
            expectedPrices,
            "Products are not sorted by price from low to high"
        );

        Reporter.log(
            "PASS: Products are sorted by price from low to high",
            true
        );
    }
}
