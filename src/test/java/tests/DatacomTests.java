package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.PayeesPage;
import pages.PaymentsPage;
import utilities.BaseUtil;
import utilities.Constants;
import utilities.LogUtil;

import java.util.Collections;
import java.util.List;

import static utilities.ExtentTestManager.startSuite;

public class DatacomTests extends BaseUtil {

    LandingPage landingPage;
    PayeesPage payeesPage;
    PaymentsPage paymentsPage;

    @BeforeClass
    @Parameters("browser")
    public void createTestSuite(String browser) {
        startSuite("Datacom Tests",browser);
    }

    @Test(description = "TC1: Verify you can navigate to Payees page using the navigation menu")
    public void validateNavigationToPayeesPage() {
        try {

            landingPage = new LandingPage(super.getDriver());
            payeesPage = new PayeesPage(super.getDriver());

            //verify user is in landing page
            Assert.assertTrue(landingPage.validateIfMainMenuButtonIsDisplayed(super.getDriver(), 5),"Landing page is not loaded");

            //Expand hamburger menu and click on
            landingPage.clickHamburgerMenu();
            landingPage.clickPayeesMenuOption(super.getDriver(),5);
            extenttest.get().info("User clicks on Payees option in hamburger menu");

            //verify user is in payees page
            Assert.assertTrue(payeesPage.validateIfPayeesIsDisplayed(super.getDriver(),5), "Payees page is not loaded");
            extenttest.get().pass("User is in Payees page");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    @Test(description = "TC2: Verify you can add new payee in the Payees page")
    public void validateAddNewPayee() {
        try {
            payeesPage = new PayeesPage(super.getDriver());

            //Test Data
            String payeeName = "Test-01";
            String accountNumber = "01-0001-0000001-001";

            //navigate user  to payees page
            super.getDriver().get(configProperties.getProperty("payees.page.url"));

            //verify user is in payees page
            Assert.assertTrue(payeesPage.validateIfPayeesIsDisplayed(super.getDriver(),10), "Payees page is not loaded");
            extenttest.get().info("User is in Payees page");

            //add new payee
            addNewPayee(payeesPage,payeeName, accountNumber.replace("-",""));
            extenttest.get().pass("Payee added successfully!");

            //verify payee is returned in search results
            payeesPage.enterSearchCriteria(payeeName);
            Assert.assertEquals(payeesPage.getNumberOfRecordsInSearchResults(),1);

            //verify payee name and account number in search results
            Assert.assertEquals(payeesPage.getPayeeNameFromSearchResults(), payeeName);
            Assert.assertEquals(payeesPage.getBankAccountNumberFromSearchResults(), accountNumber);
            extenttest.get().pass("Payee returned in search results!");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    @Test(description = "TC3: Verify payee name is a required field")
    public void validateMandatoryFieldsInAddPayeeForm() {
        try {
            payeesPage = new PayeesPage(super.getDriver());

            //Test Data
            String payeeName = "Test-02";

            //navigate user  to payees page
            super.getDriver().get(configProperties.getProperty("payees.page.url"));

            //verify user is in payees page
            Assert.assertTrue(payeesPage.validateIfPayeesIsDisplayed(super.getDriver(),10), "Payees page is not loaded");
            extenttest.get().info("User is in Payees page");

            //click add payee button in page
            extenttest.get().info("Adding new payee");
            payeesPage.clickAddPayeeBtnInPage();

            //click add button to add payee
            payeesPage.clickAddPayeeInForm(super.getDriver(),5);

            //verify error message is shown for empty payee field
            Assert.assertEquals(payeesPage.getFloatingErrorMessage(), Constants.EMPTY_PAYEE_NAME_FLOATING_ERROR);
            payeesPage.clickOnFloatingErrorMessage();
            Assert.assertEquals(payeesPage.getPayeeFormErrorMessage(), Constants.EMPTY_PAYEE_NAME_FORM_ERROR);

            //verify error in form error message is hidden after populating payee name
            payeesPage.enterPayeeName(super.getDriver(),payeeName, 5);
            payeesPage.selectSuggestedPayeeNameFromDropdown(super.getDriver(),5);

            boolean isFloatingErrorMessageDisplayed = true;
            if(!payeesPage.isFloatingErrorMessageDisplayed()){
                isFloatingErrorMessageDisplayed = false;
            }
            Assert.assertFalse(isFloatingErrorMessageDisplayed, "Floating error message for empty payee field is still displayed");

            boolean isFormErrorMessageDisplayed = true;
            if(payeesPage.isFormErrorMessageDisplayed(super.getDriver()) == 0){
                isFormErrorMessageDisplayed = false;
            }
            Assert.assertFalse(isFormErrorMessageDisplayed, "Form error message for empty payee field is still displayed");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    @Test(description = "TC4: Verify that payees can be sorted by name")
    public void validatePayeeListIsSorted() {
        try {
            payeesPage = new PayeesPage(super.getDriver());

            //Test Data
            String payeeName = "Test-02";
            String accountNumber = "0200010000001001";

            //navigate user  to payees page
            super.getDriver().get(configProperties.getProperty("payees.page.url"));

            //verify user is in payees page
            Assert.assertTrue(payeesPage.validateIfPayeesIsDisplayed(super.getDriver(),10), "Payees page is not loaded");
            extenttest.get().info("User is in Payees page");

            //add new payee
            addNewPayee(payeesPage,payeeName, accountNumber);

            //verify payee list is sorted A-Z
            Assert.assertTrue(checkIfListIsSortedAtoZ(payeesPage.getPayeeList(super.getDriver())));
            extenttest.get().info("Default payee list is sorted alphabetically");

            //verify payee list is sorted Z-A after clicking on Name
            payeesPage.clickNameButtonToSortPayees();
            Assert.assertTrue(checkIfListIsSortedZtoA(payeesPage.getPayeeList(super.getDriver())));
            extenttest.get().info("Payee list is sorted in descending order");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    @Test(description = "TC5: Navigate to Payments page")
    public void validateNavigationToPaymentsPage() {
        try {

            landingPage = new LandingPage(super.getDriver());
            paymentsPage = new PaymentsPage(super.getDriver());
            payeesPage = new PayeesPage(super.getDriver());

            //verify user is in landing page
            Assert.assertTrue(landingPage.validateIfMainMenuButtonIsDisplayed(super.getDriver(),5),"Landing page is not loaded");

            //Expand hamburger menu and click on
            landingPage.clickHamburgerMenu();
            landingPage.clickPayOrTransferMenuOption(super.getDriver(),5);
            extenttest.get().info("User clicks on Pay or transfer option in hamburger menu");

            //verify user is in payments page
            Assert.assertTrue(paymentsPage.validateIfUserIsInPaymentsPage(super.getDriver(),5), "Payments page is not loaded");
            extenttest.get().pass("User is in Payments page");

            //select from account
            paymentsPage.clickChooseSourceAccount();
            paymentsPage.enterAccountSearchText(super.getDriver(),"Everyday", 5);
            paymentsPage.selectAccountFromList(super.getDriver(),"Everyday");
            Assert.assertEquals(paymentsPage.getSelectedSourceAccountName(),"Everyday");
            extenttest.get().pass("Source account is selected");

            //get source account current balance
            String currentSrc = paymentsPage.getSelectedSourceAccountBalance();

            //select to account
            paymentsPage.clickChooseDestinationAccount();
            paymentsPage.enterAccountSearchText(super.getDriver(),"Bills ", 5);
            paymentsPage.selectAccountFromList(super.getDriver(),"Bills ");
            Assert.assertEquals(paymentsPage.getSelectedDestinationAccountName(),"Bills");
            extenttest.get().pass("Destination account is selected");

            //get destination account current balance
            String currentDest = paymentsPage.getSelectedDestinationAccountBalance();

            //fill amount
            paymentsPage.enterTransferAmount("500");

            //click on transfer
            paymentsPage.clickTransferButton();

            //verify success message
            Assert.assertEquals("Transfer successful",payeesPage.getNotificationDisplayedInPage(super.getDriver(),5));
            extenttest.get().pass("Transaction is successful");

            //verify updated account balance in source account
            String newSrc = paymentsPage.getAccountBalanceForAccountInHomePage(super.getDriver(),"Everyday");
            String newDest = paymentsPage.getAccountBalanceForAccountInHomePage(super.getDriver(),"Bills ");
            Assert.assertEquals(extractAccountBalanceFromValueInPaymentForm(currentSrc)-500, extractAccountBalanceFromValueInHomePage(newSrc));
            Assert.assertEquals(extractAccountBalanceFromValueInPaymentForm(currentDest)+500, extractAccountBalanceFromValueInHomePage(newDest));
            extenttest.get().pass("Account balances are updated successfully");

        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    public void addNewPayee(PayeesPage payeesPageObj, String payeeName, String accountNumber){
        try{
            //click add payee button in page
            extenttest.get().info("Adding new payee");
            payeesPageObj.clickAddPayeeBtnInPage();

            //enter name and account number
            payeesPageObj.enterPayeeName(super.getDriver(),payeeName, 5);
            payeesPageObj.selectSuggestedPayeeNameFromDropdown(super.getDriver(),5);
            payeesPageObj.enterPayeeAccountNumber(accountNumber);

            //click add button to add payee
            payeesPageObj.clickAddPayeeInForm(super.getDriver(),5);

            //verify payee added message
            Assert.assertEquals(payeesPageObj.getNotificationDisplayedInPage(super.getDriver(),5),"Payee added");
            extenttest.get().pass("Payee added successfully!");
        }catch (Exception e) {
            LogUtil.error(e.getMessage());
            throw e;
        }
    }

    public boolean checkIfListIsSortedAtoZ(List<String> payeeNameList){
        List<String> list = payeeNameList;
        boolean isListSorted = true;
        for(int i=0;i>list.size();i++){
            if(list.get(i-1).compareTo(list.get(i)) > 0){
                isListSorted = false;
            }
        }
        return isListSorted;
    }

    public boolean checkIfListIsSortedZtoA(List<String> payeeNameList){
        List<String> list = payeeNameList;
        List<String> sortedList = list;
        Collections.sort(sortedList,Collections.reverseOrder());
        boolean isListSorted = true;
        if(!list.equals(sortedList)){
            isListSorted = false;
        }
        return isListSorted;
    }

    public double extractAccountBalanceFromValueInPaymentForm(String value){
        String temp = value.substring(1,value.indexOf(" "));
        double extractedValue = Double.parseDouble(temp.replace(",",""));
        return extractedValue;
    }

    public double extractAccountBalanceFromValueInHomePage(String value){
        String temp = value.replace(",","");
        double extractedValue = Double.parseDouble(temp);
        return extractedValue;
    }
}
