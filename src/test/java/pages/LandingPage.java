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

    @FindBy(id = "sntLogin")
    @CacheLookup
    WebElement btn_login;

    @FindBy(xpath = "//li[contains(@id,'menu-item')]//a[contains(text(),'Pricing')]")
    @CacheLookup
    WebElement btn_pricing;

    public boolean validateIfLoginIsDisplayed(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_login, seconds);
        return btn_login.isDisplayed();
    }

    public void clickLoginButton(){
        btn_login.click();
    }

    public void clickPricingButton(){
        btn_pricing.click();
    }

}
