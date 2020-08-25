package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Utilities;

public class CheckboxPage {

    private WebDriver _driver;

    public CheckboxPage(WebDriver driver) {

        _driver = driver;
        PageFactory.initElements(_driver, this);
    }

    @FindBy(css = "#checkboxes input")
    private WebElement checkbox1;

    @FindBy(css = "#checkboxes input:nth-of-type(2)")
    private WebElement checkbox2;

    public void setCheckbox1() {
        Utilities.setCheckBox(checkbox1);
    }

    public void clearCheckbox2() {
        Utilities.clearCheckBox(checkbox2);
    }

    public boolean getCheckbox1State(){

        return Utilities.getCheckBoxState(checkbox1);
    }

    public boolean getCheckbox2State(){

        return Utilities.getCheckBoxState(checkbox2);
    }







}
