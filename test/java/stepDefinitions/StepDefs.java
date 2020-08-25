package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import helperClasses.HelperClass;
import pageObjects.*;
import utilities.StartupTearDown;
import utilities.Utilities;

import static utilities.Utilities.driver;

public class StepDefs {
	
	private HelperClass helperClass;
	private WebDriver _driver;
	private Utilities utilities;
	private EnergyJourneyHomepage energyJourneyHomepage;
	private ElectricitySupplierPage electricitySupplierPage;
	
	//Variable shared between step defs
	private String webpageTitle;	
	
	public StepDefs() {

		_driver = new StartupTearDown().setUp();
		utilities = new Utilities(_driver);
		helperClass = new HelperClass(_driver);
		energyJourneyHomepage = new EnergyJourneyHomepage(_driver);
		electricitySupplierPage = new ElectricitySupplierPage(_driver);
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Sanity Test Step Defs
	///////////////////////////////////////////////////////////////////////////////////
	
	@Given("^I am on the google homepage$")
	public void i_am_on_the_google_homepage() throws Throwable {
		helperClass.goToGoogleHomePage();
		webpageTitle = new GooglePage(_driver).getPageTitle();
	}

	@When("^I search for webdriver$")
	public void i_search_for_webdriver() throws Throwable {
		helperClass.searchFor("webdriver");
	}
	
	@Then("^the page title is as expected$")
	public void the_page_title_is_as_expected() throws Throwable {
		utilities.waitForWebPageTitleToChangeFromOriginal(webpageTitle);
		helperClass.checkPageTitle();
	}

	///////////////////////////////////////////////////////////////////////////////////
	// CTM Challenge Step Defs
	///////////////////////////////////////////////////////////////////////////////////

	@Given("I am on the Energy Journey homepage")
	public void iAmOnTheEnergyJourneyHomepage() {

		helperClass.goToEnergyJourneyHomePage();
	}

	@When("I submit my postcode")
	public void iSubmitMyPostcode() throws Exception {

		energyJourneyHomepage.submitPostCode();
	}

	@And("I select electricity only for the purposes of price comparison")
	public void iSelectElectricityOnlyForThePurposesOfPriceComparison() throws Exception {

		energyJourneyHomepage.selectElectricityForPriceComparison();
	}

	@And("I select the first supplier shown")
	public void iSelectTheFirstSupplierShown() {

		energyJourneyHomepage.selectTheFirstSupplierShown();
	}

	@And("I move onto the electricity tariff page")
	public void iMoveOntoTheNextPage() throws Exception {

		energyJourneyHomepage.moveToElectricityTariffPage();
	}

	@And("I enter {int} kwh as my monthly electricity usage")
	public void iEnterKwhAsMyMonthlyElectricityUsage(int kwhUsage) {

		electricitySupplierPage.enterKwhElectricityUsage(kwhUsage);
	}

	@And("I enter the date of my most recent bill")
	public void iEnterADateOfMyMostRecentBill() {

		electricitySupplierPage.enterDateOfMostRecentBill();
	}

	@And("I move onto the Your Details page")
	public void iMoveOntoTheYourDetailsPage() throws Exception {

		electricitySupplierPage.moveOntoYourDetailsPage();
	}

	@And("I select Fixed Tariff")
	public void iSelectFixedTariff() {

		driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourPreferences_WhatTariffAreYouInterestedIn label")).click();
	}

	@And("I select Monthly Direct Debit")
	public void iSelectMonthlyDirectDebit() {

		driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourPreferences_HowDoYouWantToPayForYourEnergy label")).click();
	}

	@And("I enter {string} as my email address")
	public void iEnterAsMyEmailAddress(String emailAddress) {

		driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourContactDetails_WhatIsYourEmailAddress")).sendKeys("" + emailAddress);
	}

	@And("I select Do Not Contact as my contact preference")
	public void iSelectDoNotContactAsMyContactPreference() {

		driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_YourContactDetails_LetUsKeepYouUpToDate div:nth-of-type(5)")).click();
	}

	@And("I select the Confirm check box if required")
	public void iSelectTheConfirmCheckBox() throws InterruptedException {

		// Detect if the confirm checkbox is present and click it if so
		// Presence of the confirm checkbox seems fairly random
		if (Utilities.doesElementExist(By.id("IConfirm"), 500))
		{
			driver.findElement(By.cssSelector("#IConfirm div:nth-of-type(2) label")).click();
			System.out.println("Confirm checkbox detected - has been ticked");
		}
	}

	@And("I click the Go To Prices button")
	public void iClickTheGoToPricesButton() throws Exception {

		driver.findElement(By.cssSelector("#EnergyComparison_YourDetails_GoToPrices")).click();
		Utilities.waitForElementToExistUsesID("EnergyComparison_YourResults_Summary_EditElectricitySupplier");
		Utilities.waitForElementToBeClickableSafe(driver.findElement(By.cssSelector("#EnergyComparison_YourResults_Summary_EditElectricitySupplier")));
	}

	@Then("I see that the section titled {string} is present")
	public void iSeeThatTheSectionTitledIsPresent(String textToFind) throws Exception {

		Utilities.waitforTextToAppear("Cheapest switchable tariff");
	}

	// The 'After' hook must be here in the StepDefs file or CucumberJVM will ignore it!

	@After
	public void tearDown() {

		System.out.println("Teardown - closing Webdriver.");
		driver.quit();
	}
}
