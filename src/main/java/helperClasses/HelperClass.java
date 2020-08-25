package helperClasses;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebDriver;
import pageObjects.GooglePage;

public class HelperClass {
	
	private String sanityTestUrl = "http://www.google.com/";
	private String energyHomepageUrl = "https://energy-journey.comparethemarket.com/your-supplier?AFFCLIE=TSTT";

	private WebDriver _driver;
	private GooglePage googlePage;
	
	public HelperClass(WebDriver driver)
	{
		_driver = driver;
		googlePage = new GooglePage(_driver);
	}

	public void goToGoogleHomePage(){

		_driver.get(sanityTestUrl);
	}
	
	public void checkPageTitle(){

		assertEquals("webdriver - Google Search", googlePage.getPageTitle());
	}

	public void searchFor(String searchTerm) {
		
		googlePage.populateSearchTextbox(searchTerm);
		googlePage.clickSearchButton();
	}

    public void goToEnergyJourneyHomePage() {

		_driver.get(energyHomepageUrl);
    }
}
