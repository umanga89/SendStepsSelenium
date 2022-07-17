package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.SeleniumUtil;

import java.util.ArrayList;
import java.util.List;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    //Page buttons
    @FindBy(xpath = "//button[@data-elaction='click_login']")
    WebElement btn_continue_and_login;


    //Page labels
    @FindBy(xpath = "//span[contains(text(),'Payees')]/parent::h1")
    @CacheLookup
    WebElement title_payees_page;



    //Page textboxes
    @FindBy(xpath = "//input[@placeholder='Enter your e-mail']")
    @CacheLookup
    WebElement txt_username;

    @FindBy(xpath = "//input[@placeholder='Password']")
    @CacheLookup
    WebElement txt_password;



    public boolean validateIfLoginIsDisplayed(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,txt_username, seconds);
        return txt_username.isDisplayed();
    }
    public void enterUsername(String username){
        txt_username.sendKeys(username);
    }

    public void enterPassword(WebDriver driver, int seconds, String password){
        SeleniumUtil.waitForElementToBeVisible(driver,txt_password, seconds);
        txt_password.sendKeys(password);
    }

    public void clickContinueOrLogin(){
        btn_continue_and_login.click();
    }
}