package utilities;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class StartupTearDown {
	
	private WebDriver driver;
	private String browserName;
	private String browserVersion;	
	
	public WebDriver setUp() {

		driver = new ChromeDriver();

		// Examples of other drivers we could use:

			//driver = new FirefoxDriver();

			//driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), DesiredCapabilities.chrome());

			//driver = new  InternetExplorerDriver();

			//driver = new  SafariDriver();

		driver.manage().window().maximize();

		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();

		browserName = caps.getBrowserName();

		browserVersion = caps.getVersion();

		System.out.println("Automated test run. We're running on " + browserName + " " + browserVersion);

		return driver;
	}	
	

}
