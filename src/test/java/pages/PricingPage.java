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

public class PricingPage {

    WebDriver driver;

    public PricingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Page labels
    @FindBy(xpath = "//h1[text()='Choose your plan.']")
    @CacheLookup
    WebElement lbl_choose_your_plan;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[contains(@class,'plan-title')]")
    @CacheLookup
    List<WebElement> lbl_yearly_plan_titles;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Free']/parent::div//span[contains(@class,'plan-price') and @data-currency='eur']")
    @CacheLookup
    WebElement lbl_yearly_free_plan_price;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Starter']/parent::div//span[contains(@class,'plan-price') and @data-currency='eur']")
    @CacheLookup
    WebElement lbl_yearly_starter_plan_price;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Professional']/parent::div//span[contains(@class,'plan-price') and @data-currency='eur']")
    @CacheLookup
    WebElement lbl_yearly_professional_plan_price;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Enterprise']/parent::div//span[contains(@class,'plan-price')]")
    @CacheLookup
    WebElement lbl_yearly_enterprise_plan_price;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Free']/parent::div//div[contains(@class,'pricing-plan-features')]/span")
    @CacheLookup
    List<WebElement> lbl_yearly_free_plan_features;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Starter']/parent::div//div[contains(@class,'pricing-plan-features')]/span")
    @CacheLookup
    List<WebElement> lbl_yearly_starter_plan_features;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Professional']/parent::div//div[contains(@class,'pricing-plan-features')]/span")
    @CacheLookup
    List<WebElement> lbl_yearly_professional_plan_features;

    @FindBy(xpath = "//div[@id='pricingOverview']//div[@data-table-type='yearly']//span[text()='Enterprise']/parent::div//div[contains(@class,'pricing-plan-features')]/span")
    @CacheLookup
    List<WebElement> lbl_yearly_enterprise_plan_features;

    public boolean validateIfUserIsInPricingPage(WebDriver driver, int seconds){
        SeleniumUtil.waitForElementToBeVisible(driver,lbl_choose_your_plan, seconds);
        return lbl_choose_your_plan.isDisplayed();
    }

    public int getPricingTierCount(){
        return lbl_yearly_plan_titles.size();
    }

    public List<String> getPricingTierTitles(){
        List<String> pricingTierTitleList = new ArrayList<>();
        for(WebElement element : lbl_yearly_plan_titles){
            pricingTierTitleList.add(element.getText());
        }
        return pricingTierTitleList;
    }

    public String getPricingForFreePlan(){
        return lbl_yearly_free_plan_price.getText();
    }

    public String getPricingForStarterPlan(){
        return lbl_yearly_starter_plan_price.getText();
    }

    public String getPricingForProfessionalPlan(){
        return lbl_yearly_professional_plan_price.getText();
    }

    public String getPricingForEnterprisePlan(){
        return lbl_yearly_enterprise_plan_price.getText();
    }
}