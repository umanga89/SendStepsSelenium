package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SeleniumUtil;

public class LandingPage {

    WebDriver driver;

    public LandingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//button[contains(@class,'main-menu-btn')]")
    @CacheLookup
    WebElement btn_main_menu;

    @FindBy(xpath = "//span[contains(text(),'Payees')]/parent::a")
    @CacheLookup
    WebElement btn_main_menu_payees;

    @FindBy(xpath = "//span[contains(text(),'Pay or transfer')]/parent::button")
    @CacheLookup
    WebElement btn_main_menu_pay_or_transfer;

    public boolean validateIfMainMenuButtonIsDisplayed(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_main_menu, seconds);
        return btn_main_menu.isDisplayed();
    }

    public void clickHamburgerMenu(){
        btn_main_menu.click();
    }

    public void clickPayeesMenuOption(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_main_menu_payees, seconds);
        btn_main_menu_payees.click();
    }

    public void clickPayOrTransferMenuOption(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_main_menu_pay_or_transfer, seconds);
        btn_main_menu_pay_or_transfer.click();
    }
}
