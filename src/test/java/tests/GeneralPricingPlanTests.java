package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.LoginPage;
import pages.PricingPage;
import utilities.BaseUtil;
import utilities.Constants;
import utilities.ExcelUtil;
import utilities.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utilities.ExtentTestManager.startSuite;

public class GeneralPricingPlanTests extends BaseUtil {

    LandingPage landingPage;
    LoginPage loginPage;
    PricingPage pricingPage;

    @BeforeClass
    @Parameters("browser")
    public void createTestSuite(String browser) {
        startSuite("General Pricing Tests",browser);
    }

    @Test(description = "Scenario 1: Four Pricing tiers should be displayed to user in pricing page with full feature table")
    public void validateNavigationToPricingPage() throws IOException {
        try {
            Map<String,Integer> columnIndex = ExcelUtil.getColumnsIndexesFromExcel(System.getProperty("user.dir")+"/src/test/java/testdata/TestData.xlsx", "BusinessPlans");
            List<Map<Integer,String>> excelData = ExcelUtil.getDataForRowFromExcel(System.getProperty("user.dir")+"/src/test/java/testdata/TestData.xlsx", "BusinessPlans");

            landingPage = new LandingPage(super.getDriver());
            loginPage = new LoginPage(super.getDriver());
            pricingPage = new PricingPage(super.getDriver());

            //verify user is in landing page
            Assert.assertTrue(landingPage.validateIfLoginIsDisplayed(super.getDriver(), 5),"Landing page is not loaded");

            //click on login button
            landingPage.clickLoginButton();
            loginPage.validateIfLoginIsDisplayed(super.getDriver(),5);

            //Login with valid credentials
            loginWithValidCredentials(loginPage,configProperties.getProperty("username"),configProperties.getProperty("password"));

            //verify user is in payees page
            super.getDriver().get(configProperties.getProperty("web.url"));
            landingPage.clickPricingButton();
            Assert.assertTrue(pricingPage.validateIfUserIsInPricingPage(super.getDriver(),5), "Pricing page is not loaded");
            extenttest.get().pass("User is in Pricing page");

            //very 4 pricing tiers are displayed to user in business plans
            Assert.assertEquals(pricingPage.getPricingTierCount(), excelData.size(), "4 pricing plans were expected");
            extenttest.get().pass("4 pricing plans are displayed under Business plans");

            //verify names of each pricing tier
            List<String> pricingPlanTitles = pricingPage.getPricingTierTitles();
            int count=0;
            for(String title : pricingPlanTitles){
                Assert.assertEquals(title, excelData.get(count).get(columnIndex.get("Plan name")), "Displayed pricing plans are not correct");
                count++;
            }
            extenttest.get().pass("Pricing plans titles are verified under Business plans");

            //verify monthly price (yearly plan) for each pricing tier
            Assert.assertEquals(pricingPage.getPricingForFreePlan().replace("\n",""), excelData.get(0).get(columnIndex.get("Monthly Price (Monthly Plan)")), "Free plan price is not matched");

            Assert.assertEquals(pricingPage.getPricingForStarterPlan().replace("\n",""), excelData.get(1).get(columnIndex.get("Monthly Price (Monthly Plan)")), "Starter plan price is not matched");

            Assert.assertEquals(pricingPage.getPricingForProfessionalPlan().replace("\n",""), excelData.get(2).get(columnIndex.get("Monthly Price (Monthly Plan)")), "Professional plan price is not matched");

            Assert.assertEquals(pricingPage.getPricingForEnterprisePlan().replace("\n",""), excelData.get(3).get(columnIndex.get("Monthly Price (Monthly Plan)")), "Enterprise plan price is not matched");
            extenttest.get().pass("Monthly price (Yearly plan) for each plan is verified under Business plans");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    public void loginWithValidCredentials(LoginPage loginPageObj, String username, String Password){
        try{
            //enter username
            extenttest.get().info("Enter username and click Continue");
            loginPageObj.enterUsername(username);
            loginPageObj.clickContinueOrLogin();

            //enter password
            extenttest.get().info("Enter Password and click Continue");
            loginPageObj.enterPassword(super.getDriver(), 5, Password);
            loginPageObj.clickContinueOrLogin();

            extenttest.get().pass("User logged in successfully!");
        }catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }
}
