package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import helperClasses.HelperClass;
import org.openqa.selenium.WebDriver;
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
	private YourDetailsPage yourDetailsPage;
	
	//Variable shared between step defs
	private String webpageTitle;	
	
	public StepDefs() {

		_driver = new StartupTearDown().setUp();
		utilities = new Utilities(_driver);
		helperClass = new HelperClass(_driver);
		energyJourneyHomepage = new EnergyJourneyHomepage(_driver);
		electricitySupplierPage = new ElectricitySupplierPage(_driver);
		yourDetailsPage = new YourDetailsPage(_driver);
	}

	///////////////////////////////////////////////////////////////////////////////////
	// Sanity Test Step Defs
	///////////////////////////////////////////////////////////////////////////////////
	
	@Given("^I am on the google homepage$")
	public void i_am_on_the_google_homepage() {

		helperClass.goToGoogleHomePage();
		webpageTitle = new GooglePage(_driver).getPageTitle();
	}

	@When("^I search for webdriver$")
	public void i_search_for_webdriver() {

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

		yourDetailsPage.selectFixedTariff();
	}

	@And("I select Monthly Direct Debit")
	public void iSelectMonthlyDirectDebit() {

		yourDetailsPage.selectMonthlyDirectDebit();
	}

	@And("I enter {string} as my email address")
	public void iEnterAsMyEmailAddress(String emailAddress) {

		yourDetailsPage.enterEmailAdddress(emailAddress);
	}

	@And("I select Do Not Contact as my contact preference")
	public void iSelectDoNotContactAsMyContactPreference() {

		yourDetailsPage.selectDoNotContact();
	}

	@And("I select the Confirm check box if required")
	public void iSelectTheConfirmCheckBox() throws InterruptedException {

		yourDetailsPage.selectConfirmCheckboxIfRequired();
	}

	@And("I click the Go To Prices button")
	public void iClickTheGoToPricesButton() throws Exception {

		yourDetailsPage.clickGoToPricesButton();
	}

	@Then("I see that the section titled {string} is present")
	public void iSeeThatTheSectionTitledIsPresent(String textToFind) throws Exception {

		Utilities.waitforTextToAppear(textToFind);
	}

	@When("I populate the Energy Journey start page with standard information")
	public void iPopulateTheEnergyJourneyStartPageWithStandardInformation() throws Exception {

		iSubmitMyPostcode();
		iSelectElectricityOnlyForThePurposesOfPriceComparison();
		iSelectTheFirstSupplierShown();
		iMoveOntoTheNextPage();
	}

	@And("I populate the Electricity Tariff page with standard information using {int} kwh as my monthly electricity usage")
	public void iPopulateTheElectricityTariffPageWithStandardInformationWithKwhAsMyMonthlyElectricityUsage(int kwhUsage) throws Exception {

		iEnterKwhAsMyMonthlyElectricityUsage(kwhUsage);
		iEnterADateOfMyMostRecentBill();
		iMoveOntoTheYourDetailsPage();
	}

	@And("I populate the Your Details page with standard information using {string} as my email address")
	public void iPopulateTheYourDetailsPageWithStandardInformationUsingAsMyEmailAddress(String emailAddress) throws Exception {

		iSelectFixedTariff();
		iSelectMonthlyDirectDebit();
		iEnterAsMyEmailAddress(emailAddress);
		iSelectDoNotContactAsMyContactPreference();
		iSelectTheConfirmCheckBox();
		iClickTheGoToPricesButton();
	}

	// The 'After' hook must be here in the StepDefs file or CucumberJVM will ignore it!

	@After
	public void tearDown() {

		System.out.println("Teardown - closing Webdriver.");
		driver.quit();
	}

}
