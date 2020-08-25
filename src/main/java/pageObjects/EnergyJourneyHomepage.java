package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utilities.Utilities;

public class EnergyJourneyHomepage {

    private WebDriver driver;

    public EnergyJourneyHomepage(WebDriver _driver)
    {
        driver = _driver;
    }

    public void submitPostCode() throws Exception {

        String whatsYourPostcodeID = "EnergyComparison_YourSupplier_YourCurrentSupplier_WhatsYourPostcode";

        // Wait for postcode input field to exist and be accessible
        Utilities.waitForElementToExistUsesID(whatsYourPostcodeID);
        Utilities.waitForElementVisible(driver.findElement(By.id(whatsYourPostcodeID)));

        driver.findElement(By.id(whatsYourPostcodeID)).sendKeys("PE2 6YS");
        driver.findElement(By.id("EnergyComparison_YourSupplier_YourCurrentSupplier_FindPostcode")).click();

        Utilities.waitforTextToAppear("Change postcode");

        // Wait for the element to exist otherwise the required element will not be on the DOM during the load process.
        Utilities.waitForElementToExistUsesID("EnergyComparison_YourSupplier_YourCurrentSupplier_CurrentEnergySupplier");
    }

    public void selectElectricityForPriceComparison() throws Exception {

        String yourCurrentSupplierIDComparison = "EnergyComparison_YourSupplier_YourCurrentSupplier_WhatWouldYouLikeToCompare";

        Utilities.waitForElementToBeClickableSafe(driver.findElement(By.cssSelector("#" + yourCurrentSupplierIDComparison +" label")));
        driver.findElement(By.cssSelector("#" + yourCurrentSupplierIDComparison + " div:nth-of-type(2)")).click();
        Utilities.waitForElementToExistUsesID("risk.electricity.currentSupply.supplier");
    }

    public void selectTheFirstSupplierShown() {

        String yourCurrentSupplierID = "EnergyComparison_YourSupplier_YourCurrentSupplier_CurrentElectricitySupplier";

        // Bring the element into the viewport, so that's never an issue
        WebElement element = driver.findElement(By.cssSelector("#" + yourCurrentSupplierID + " div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();

        // Select the first energy supplier. Note this means that selection is non-deterministic. If we wish to be sure we're always selecting the same supplier, this will require more code.
        driver.findElement(By.cssSelector("#" + yourCurrentSupplierID + " label")).click();
    }

    public void moveToElectricityTariffPage() throws Exception {

        String yourCurrentTariffID = "EnergyComparison_HaveBill_YourEnergy_YourElectricity_WhatElectricityTariffAreYouOn";

        // Select the 'Next' button
        driver.findElement(By.id("EnergyComparison_YourSupplier_Next")).click();

        // Ensure that clicking the next button has resulted in us navigating to the correct page
        Utilities.waitForElementToExistUsesID(yourCurrentTariffID);
        Utilities.waitForElementToBeClickableSafe(driver.findElement(By.cssSelector("#" + yourCurrentTariffID + " label")));
    }
}
