package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.*;

import java.util.Objects;

public class TestListenerImpl extends BaseUtil implements ITestListener {

    ExtentReports extent = ExtentReportUtil.initializeExtentReport();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getDescription();
    }

    @Override
    public void onStart(ITestContext context) {
        try{
            //getting home directory
            BaseUtil.base_directory = System.getProperty("user.dir");

            //loading config property file based on environment
            String env = context.getCurrentXmlTest().getParameter("env");

            if(env!=null) {
                switch (env) {

                    case "dev":
                        BaseUtil.configProperties = PropertiesUtil.loadProperties(BaseUtil.base_directory + "/src/test/java/config/dev.properties");
                        break;
                    case "":
                        throw new Exception("env parameter should be either \"env\" or \"stg\" in testng.xml");
                }
            }else{
                throw new Exception("env parameter is not configured in testng.xml");
            }

            //Initialize extent report
            extentreport.set(extent);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LogUtil.info(iTestContext.getName()+" is finished!");
        extentreport.get().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        test = extentsuite.get().createNode(iTestResult.getMethod().getDescription());
        extenttest.set(test);
        LogUtil.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LogUtil.info(getTestMethodName(iTestResult) + " test is succeed.");
        extenttest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LogUtil.error(getTestMethodName(iTestResult) + " test is failed.\n"+iTestResult.getThrowable());
        //Get driver from BaseTest and assign to local webdriver variable.
        Object testClass = iTestResult.getInstance();
        WebDriver driver = ((BaseUtil) testClass).getDriver();
        //Take base64Screenshot screenshot for extent reports
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
        extenttest.get().log(Status.FAIL, iTestResult.getThrowable(),
                extenttest.get().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LogUtil.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        extenttest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LogUtil.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}
