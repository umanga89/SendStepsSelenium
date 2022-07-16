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

public class PaymentsPage {

    WebDriver driver;

    public PaymentsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Page buttons
    @FindBy(xpath = "//button[@data-monitoring-label='Transfer Form Submit']")
    @CacheLookup
    WebElement btn_pay_or_transfer;

    @FindBy(xpath = "//button[@data-testid='from-account-chooser']")
    @CacheLookup
    WebElement btn_choose_source_account;

    @FindBy(xpath = "//button[@data-testid='to-account-chooser']")
    @CacheLookup
    WebElement btn_choose_destination_account;

    @FindBy(xpath = "//li[@data-testid='to-account-accounts-tab']")
    @CacheLookup
    WebElement btn_account_in_choose_destination_account;

    //Page text boxes
    @FindBy(xpath = "//input[@placeholder='Search']")
    WebElement txt_search_account;

    @FindBy(name = "amount")
    @CacheLookup
    WebElement txt_amount;

    //Page labels
    @FindBy(xpath = "//button[@data-testid='from-account-chooser']//p[contains(@class,'name')]")
    @CacheLookup
    WebElement lbl_selected_source_account_name;

    @FindBy(xpath = "//button[@data-testid='from-account-chooser']//p[contains(@class,'balance')]")
    @CacheLookup
    WebElement lbl_selected_source_account_balance;

    @FindBy(xpath = "//button[@data-testid='to-account-chooser']//p[contains(@class,'name')]")
    @CacheLookup
    WebElement lbl_selected_destination_account_name;

    @FindBy(xpath = "//button[@data-testid='to-account-chooser']//p[contains(@class,'balance')]")
    @CacheLookup
    WebElement lbl_selected_destination_account_balance;

    public boolean validateIfUserIsInPaymentsPage(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_pay_or_transfer, seconds);
        return btn_pay_or_transfer.isDisplayed();
    }

    public void clickChooseSourceAccount(){
        btn_choose_source_account.click();
    }

    public void enterAccountSearchText(WebDriver driver, String accountName, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,txt_search_account, seconds);
        txt_search_account.sendKeys(accountName);
    }

    public void selectAccountFromList(WebDriver driver, String accountName){
        driver.findElement(By.xpath("//p[text()='"+accountName+"']/ancestor::button")).click();
    }

    public void clickChooseDestinationAccount(){
        btn_choose_destination_account.click();
    }

    public String getSelectedSourceAccountName(){
        return lbl_selected_source_account_name.getText();
    }

    public String getSelectedSourceAccountBalance(){
        return lbl_selected_source_account_balance.getText();
    }

    public String getSelectedDestinationAccountName(){
        return lbl_selected_destination_account_name.getText();
    }

    public String getSelectedDestinationAccountBalance(){
        return lbl_selected_destination_account_balance.getText();
    }

    public void enterTransferAmount(String amount){
        txt_amount.sendKeys(amount);
    }

    public String getAccountBalanceForAccountInHomePage(WebDriver driver, String accountName){
        return driver.findElement(By.xpath("//h3[text()='"+accountName+"']/ancestor::div[contains(@class,'account-info')]//span[@class='account-balance']")).getText();
    }

    public void clickTransferButton(){
        btn_pay_or_transfer.click();
    }
}