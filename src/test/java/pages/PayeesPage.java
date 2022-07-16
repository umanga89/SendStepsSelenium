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

public class PayeesPage {

    WebDriver driver;

    public PayeesPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    //Page buttons
    @FindBy(xpath = "//div[@class='Row']//button[@aria-label='Add payee']")
    @CacheLookup
    WebElement btn_add_payee_top_row;

    @FindBy(xpath = "//a[@data-cb-type='new-payee']")
    @CacheLookup
    WebElement btn_suggested_payee_name;

    @FindBy(xpath = "//div[@class='row controls']//button[contains(text(),'Add')]")
    @CacheLookup
    WebElement btn_add_payee_in_add_form;

    @FindBy(xpath = "//h3[contains(@class,'js-payee-name-column')]")
    @CacheLookup
    WebElement btn_sort_payee_list;

    //Page labels
    @FindBy(xpath = "//span[contains(text(),'Payees')]/parent::h1")
    @CacheLookup
    WebElement title_payees_page;

    @FindBy(xpath = "//span[@class='selection-info new-payee']")
    @CacheLookup
    WebElement lbl_new_payee;

    @FindBy(xpath = "//span[@class='js-payee-name']")
    @CacheLookup
    WebElement lbl_payee_name_in_search_results;

    @FindBy(xpath = "//p[contains(@class,'js-payee-account')]")
    @CacheLookup
    WebElement lbl_payee_bank_account_in_search_results;

    @FindBy(xpath = "//div[contains(@class,'js-notificationShown')]//span")
    @CacheLookup
    WebElement lbl_notification;

    @FindBy(xpath = "//div[contains(@class,'general-tooltip')]//p")
    WebElement lbl_floating_error_message;

    @FindBy(xpath = "//div[@class='error-header']")
    WebElement lbl_payee_form_error_message;

    //Page textboxes
    @FindBy(id = "ComboboxInput-apm-name")
    @CacheLookup
    WebElement txt_payee_name;

    @FindBy(id = "apm-bank")
    @CacheLookup
    WebElement txt_bank_code;

    @FindBy(xpath = "//input[contains(@class,'SearchInput')]")
    @CacheLookup
    WebElement txt_search_bar;

    //Page Drop downs
    @FindBy(xpath = "//ul[contains(@class,'List')]/li")
    List<WebElement> list_search_results;


    public boolean validateIfPayeesIsDisplayed(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,title_payees_page, seconds);
        return title_payees_page.isDisplayed();
    }
    public void clickAddPayeeBtnInPage(){
        btn_add_payee_top_row.click();
    }

    public void enterPayeeName(WebDriver driver, String payeeName, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,txt_payee_name, seconds);
        txt_payee_name.sendKeys(payeeName);
    }

    public void selectSuggestedPayeeNameFromDropdown(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_suggested_payee_name, seconds);
        btn_suggested_payee_name.click();
    }

    public String getNotificationDisplayedInPage(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,lbl_notification, seconds);
        return lbl_notification.getText();
    }

    public void enterPayeeAccountNumber(String accountNumber){
        txt_bank_code.sendKeys(accountNumber);
    }

    public void clickAddPayeeInForm(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,btn_add_payee_in_add_form, seconds);
        btn_add_payee_in_add_form.click();
    }

    public void enterSearchCriteria(String payeeName){
        txt_search_bar.sendKeys(payeeName);
    }

    public int getNumberOfRecordsInSearchResults(){
        return list_search_results.size();
    }

    public String getPayeeNameFromSearchResults(){
        return lbl_payee_name_in_search_results.getText();
    }

    public String getBankAccountNumberFromSearchResults(){
        return lbl_payee_bank_account_in_search_results.getText();
    }

    public boolean isFloatingErrorMessageDisplayed(){
        //Because otherwise stale element exception is thrown
        //return driver.findElement(By.xpath("//div[contains(@class,'general-tooltip')]//p")).isDisplayed();
        return lbl_floating_error_message.isDisplayed();
    }
    public String getFloatingErrorMessage(){
        return lbl_floating_error_message.getText();
    }

    public void clickOnFloatingErrorMessage(){
        lbl_floating_error_message.click();
    }

    public int isFormErrorMessageDisplayed(WebDriver driver){
        return driver.findElements(By.xpath("//div[@class='error-header']")).size();
    }

    public String getPayeeFormErrorMessage(){
        return lbl_payee_form_error_message.getText();
    }

    public List<String> getPayeeList(WebDriver driver){
        List<String> payeeNameList = new ArrayList<>();
        for(int i=0;i>payeeNameList.size();i++){
            WebElement element = driver.findElement(By.xpath("(//p[@class='Avatar-title']/span[@class='js-payee-name'])["+i+"]"));
            payeeNameList.add(element.getText());
        }
        return payeeNameList;
    }

    public void clickNameButtonToSortPayees(){
        btn_sort_payee_list.click();
    }
}