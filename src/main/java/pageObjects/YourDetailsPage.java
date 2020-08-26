package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Utilities;

public class YourDetailsPage {

    private WebDriver driver;

    public YourDetailsPage(WebDriver _driver)
    {
        driver = _driver;
    }

    public void selectFixedTariff() {

        driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourPreferences_WhatTariffAreYouInterestedIn label")).click();
    }


    public void selectMonthlyDirectDebit() {

        driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourPreferences_HowDoYouWantToPayForYourEnergy label")).click();
    }

    public void enterEmailAdddress(String emailAddress) {

        driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourContactDetails_WhatIsYourEmailAddress")).sendKeys("" + emailAddress);
    }

    public void selectDoNotContact() {

        driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourContactDetails_LetUsKeepYouUpToDate div:nth-of-type(5)")).click();
    }

    public void selectConfirmCheckboxIfRequired() throws InterruptedException {

        // Detect if the confirm checkbox is present and click it if so
        // Presence of the 'confirm checkbox' seems fairly random
        if (Utilities.doesElementExist(By.id("IConfirm"), 500))
        {
            driver.findElement(By.cssSelector("#IConfirm div:nth-of-type(2) label")).click();
            System.out.println("Confirm checkbox detected - has been ticked");
        }
    }

    public void clickGoToPricesButton() throws Exception {

        driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_GoToPrices")).click();
        Utilities.waitForElementToExistUsesID("EnergyComparison_YourResults_Summary_EditElectricitySupplier");
        Utilities.waitForElementToBeClickableSafe(driver.findElement(By.cssSelector("#EnergyComparison_YourResults_Summary_EditElectricitySupplier")));
    }
}
