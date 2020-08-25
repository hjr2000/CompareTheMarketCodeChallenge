package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Utilities;

public class ElectricitySupplierPage {

    private WebDriver driver;

    public ElectricitySupplierPage(WebDriver _driver)
    {
        driver = _driver;
    }

    public void enterKwhElectricityUsage(int kwhUsage) {

        driver.findElement(By.cssSelector("#EnergyComparison_HaveBill_YourEnergy_YourElectricity_WhatIsYourCurrentElectricityUsage_YourCurrentUsageQuestionKwh")).sendKeys("" + kwhUsage);
    }

    public void enterDateOfMostRecentBill() {

        // Enhancement - automatically populate this field with the first day of the current month so that the test remains realistic as time passes
        driver.findElement(By.cssSelector("#EnergyComparison_HaveBill_YourEnergy_YourElectricity_DateOfPurchase_Day")).sendKeys("01");
        driver.findElement(By.cssSelector("#EnergyComparison_HaveBill_YourEnergy_YourElectricity_DateOfPurchase_Month")).sendKeys("08");
        driver.findElement(By.cssSelector("#EnergyComparison_HaveBill_YourEnergy_YourElectricity_DateOfPurchase_Year")).sendKeys("2020");
    }

    public void moveOntoYourDetailsPage() throws Exception {

        driver.findElement(By.id("EnergyComparison_HaveBill_YourEnergy_YourElectricity_Next")).click();

        // Ensure we're at the right page and that the page is available for use
        Utilities.waitForElementToExistUsesID("EnergyComparison_YourDetails");
        Utilities.waitForElementVisible(driver.findElement(By.cssSelector("#EnergyComparison_YourDetails header")));
    }
}
