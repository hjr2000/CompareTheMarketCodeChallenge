package utilities;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.GooglePage;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class Utilities {
	
	public static WebDriver driver;
    private static Wait<WebDriver> wait;
    private static final long DEFAULT_WAIT_TIMEOUT_SECS = 10;
    private static final long WAIT_FOR_PAGE_LOADED_DELAY = 3000;
    private static final long DEFAULT_SLEEP_TIMEOUT_MILLIS = 250;

	private GooglePage googlePage;
	
	public Utilities(WebDriver _driver)
	{
		driver = _driver;
        wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT_SECS, DEFAULT_SLEEP_TIMEOUT_MILLIS);
	}
	
	public void waitForWebPageTitleToChangeFromOriginal(String webpageTitle) throws Exception {

        googlePage = new GooglePage(driver);

        boolean pageChanged = false;
		for (int i = 1; i < 20; i++) {
			
			String currentWebPageTitle = googlePage.getPageTitle();
			if (!currentWebPageTitle.equals(webpageTitle))
			{
				pageChanged = true;
				break;
			}
			Thread.sleep(250);
		}
		if (!pageChanged)
			throw new Exception ("The title of the web page is not changing");
	}

    public static void waitForElementToExistUsesID(String id) throws Exception {

	    boolean atLeastOneElementFound = false;
	    for (int count = 1; count <40; count++){
            int numberOfElements = driver.findElements(By.id(id)).size();
            if (numberOfElements > 0) {
                atLeastOneElementFound = true;
                break;
            }
            Thread.sleep(250);
        }
	    if (!atLeastOneElementFound){
	        throw new Exception("No element with the ID '" + id + "' has appeared on the DOM");
        }
    }

	public static void waitForElementToBeClickableSafe(WebElement element) throws Exception {

        waitForElementToBeClickableSafe(element, 10);
    }

    public static void waitForElementToBeClickableSafe(WebElement element, int waitTimeInSeconds) throws Exception
    {

        int count = 0;
        boolean continueOn = false;
        while (!continueOn)
        {
            try
            {
                waitForWebElementToBeClickable(element, waitTimeInSeconds);

                continueOn = true;
            }
            catch (org.openqa.selenium.StaleElementReferenceException e)
            {
                System.out.println("*********** StaleElementReferenceException caught, will try again.");
                count++;
                if (count == 16)
                    throw new Exception("StaleElementReferenceException not going away when waiting for element to become clickable.");

                // Do not remove
                Thread.sleep(250);
            }
        }
    }

    public static void waitForElementVisible(WebElement webElement) {
        wait.until(visibilityOf(webElement));
    }

    private static void waitForWebElementToBeClickable(WebElement webElement, int waitTimeInSeconds){

        Wait<WebDriver> wait_local;
        int sleepTimeOutMillis = 250;
        wait_local = new WebDriverWait(driver, waitTimeInSeconds, sleepTimeOutMillis);
        wait_local.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitforTextToAppear(String textToFind) throws Exception {

        if (!doesElementExist(By.cssSelector("body"), 2000))
            throw new Exception("Cannot find 'body' region on the page");

        boolean textFound = false;
        for (int count = 0; count < 12; count++) {
            textFound = driver.findElement(By.cssSelector("body")).getText().contains(textToFind);
            if (textFound) {
                break;
            }
            Thread.sleep(250);
        }
        if (!textFound)
            throw new Exception("Searching for string '" + textToFind + "' on the current page but it appears to not be present.");
    }

    public static boolean doesElementExist(By webElementBy, int timeInMilliseconds) throws InterruptedException {

        // Wait for a maximum specified amount of time for an element to exist and return true or false depending on the outcome

        int timeElapsedInMilliseconds = 0;
        boolean elementFound = false;

        // Unavoidable thread sleep - to give the screen time to update
        Thread.sleep(500);

        while (timeElapsedInMilliseconds < timeInMilliseconds)
        {
            try {
                driver.findElement(webElementBy);
                elementFound = true;
                //log.info("Element found");
                break;
            }
            catch (NoSuchElementException ex){
                //log.info("Exception caught");
                Thread.sleep(250);
                timeElapsedInMilliseconds += 250;
            }
        }
        return elementFound;
    }

    ////////////////////////////////////////////////////////
    // Checkbox operations
    ////////////////////////////////////////////////////////

    public static void setCheckBox(WebElement checkbox) {

        // Ensure the checkbox is set regardless of its present state

        int timeInSeconds = 2;
        waitForWebElementToBeClickable(checkbox, timeInSeconds);
        if (!checkbox.isSelected())
        {
            checkbox.click();
        }
    }

    public static void clearCheckBox(WebElement checkbox) {

        // Ensure the checkbox is cleared regardless of its present state

        int timeInSeconds = 2;
        waitForWebElementToBeClickable(checkbox, timeInSeconds);
        if (checkbox.isSelected())
        {
            checkbox.click();
        }
    }

    public static void toggleCheckBox(WebElement checkbox) {

        int timeInSeconds = 2;
        waitForWebElementToBeClickable(checkbox, timeInSeconds);
        checkbox.click();
    }

    public static boolean getCheckBoxState(WebElement checkbox) {

        return checkbox.isSelected();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Drop Down Box Operations
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static void checkActualDropdownOptionsAgainstExpected(WebElement dropdownListElement, List<String> expectedOptionsList) throws Exception {

        // Set up with regard to the actual options in the drop down
        int actualOptionCount = getActualOptionCount(dropdownListElement);

        // Check we have the expected number of options coming through
        if (actualOptionCount != expectedOptionsList.size())
            throw new Exception("Was expecting " + expectedOptionsList.size() + " options in the drop down box but there are " + actualOptionCount);

        // Get the actual options
        List<String> actualOptionsList = getActualDropDownOptionsList(dropdownListElement);

        // Check off the expected options against the actual and note any missing
        String missingOptions ="";
        boolean optionFound;
        int numberOfMissingOptions = 0;
        for (String expectedOption : expectedOptionsList){
            optionFound = false;
            //for (int count = 0; count < actualOptionCount; count++) {
            for (String actualOption : actualOptionsList)
            {
                //if (actualDropdownElementSelect.getOptions().get(count).getText().equals(expectedOption)){
                if (actualOption.equals(expectedOption))
                {
                    //System.out.println("Found expectedOption '" + expectedOption + "' at position " + count);
                    optionFound = true;
                    break;
                }
            }
            if (!optionFound){
                missingOptions = missingOptions + expectedOption + ", ";
                numberOfMissingOptions++;
            }
        }

        // Report missing options
        if (!missingOptions.equals("")) {
            String exceptionTextToInsert;
            if (numberOfMissingOptions == 1){
                exceptionTextToInsert = "option is";
            }
            else
            {
                exceptionTextToInsert = "options are";
            }
            String missingOptionsTrimmed = missingOptions.substring(0, missingOptions.length()-2);
            throw new Exception("The following expected " + exceptionTextToInsert + " missing from the drop down box : " + missingOptionsTrimmed);
        }
    }

    public static List<String> getActualDropDownOptionsList(WebElement dropdownListElement) {

        Select actualDropdownElementSelect = new Select(dropdownListElement);
        int actualOptionCount = actualDropdownElementSelect.getOptions().size();

        List<String> actualOptionsList = new ArrayList<String>();
        for (int count = 0; count < actualOptionCount; count++) {
            String optionText = actualDropdownElementSelect.getOptions().get(count).getText();
            actualOptionsList.add(optionText);
        }
        return actualOptionsList;
    }

    public static void checkSpecificDropdownOptionAgainstActualOptions(WebElement dropdownListElement, String expectedOption) throws Exception {

        // Check that there is at least one option
        if (getActualOptionCount(dropdownListElement) == 0)
            throw new Exception("The drop downbox box contains no options");

        // Get the actual options
        List<String> actualOptionsList = getActualDropDownOptionsList(dropdownListElement);

        boolean foundExpectedOption = false;
        for (String actualOption : actualOptionsList){
            if (expectedOption.equals(actualOption)){
                foundExpectedOption = true;
                break;
            }
        }
        if (!foundExpectedOption)
            throw new Exception("The expected option of '" + expectedOption + "' was not found in the dropdown options list");
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Textbox operation
    ////////////////////////////////////////////////////////////////////////////////

    public static void enterTextIntoTextbox(WebElement textboxElement, String textToSend) throws Exception {

        waitForElementToBeClickableSafe(textboxElement);
        textboxElement.clear();
        sendKeysSafe(textboxElement, textToSend);
    }

    public static void sendKeysSafe(WebElement element, String stringToSend) throws Exception {

        // Keep attempting to sendKeys to an element
        boolean attemptSuccessful = false;
        for (int count = 0; count < 8; count++){
            try {
                waitForWebElementToBeClickable(element, 30);
                element.sendKeys(stringToSend);
                attemptSuccessful = true;
                break;

            }
            catch (org.openqa.selenium.InvalidElementStateException e){
                //log.info("Invalid Element State Exception Caught\n");
            }

            Thread.sleep(250);
        }
        if (!attemptSuccessful)
            throw new Exception ("Unable to enter text into text box - Invalid state exception thrown for every attempt.");
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Link clicking
    ////////////////////////////////////////////////////////////////////////////////

    // todo - code to click a link, and to wait a couple of seconds for a certain element that should appear. If it doesn't appear click the link again a maximum of three times.
    // Sometimes Webdriver thinks it has clicked on a link but it doesn't seem to be actioned on the page itself.

    ////////////////////////////////////////////////////////////////////////////////
    // Element clicking
    ////////////////////////////////////////////////////////////////////////////////

    public static void elementClickSafe(WebElement elementToClick) throws Exception {

        boolean success = false;

        for (int i = 0; i < 12; i++) {
            try {
                elementToClick.click();
                success = true;
                break;
            } catch (WebDriverException e) {
                // Do nothing, we'll try again
            }
            Thread.sleep(250);
        }
        if (!success)
            throw new Exception("WebDriver exception thrown every time we attempt to click this element");
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////////////////////////

    private static int getActualOptionCount(WebElement dropdownListElement) {

        // Get the number of actual options in the dropdown box

        Select actualDropdownElementSelect = new Select(dropdownListElement);
        int actualOptionCount = actualDropdownElementSelect.getOptions().size();
        System.out.println("actualOptionCount :" + actualOptionCount);
        return actualOptionCount;
    }
}
