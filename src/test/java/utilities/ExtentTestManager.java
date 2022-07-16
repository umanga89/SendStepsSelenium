package utilities;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager extends BaseUtil{

    public static synchronized ExtentTest startSuite(String suiteName,String browser){
        suite = extentreport.get().createTest(suiteName+"-(browser="+browser+")");
        suite.assignCategory(browser);
        extentsuite.set(suite);
        return extentsuite.get();
    }
    public static synchronized ExtentTest startTest(String testName, String desc) {
        test = extentsuite.get().createNode(testName, desc);
        extenttest.set(test);
        return extenttest.get();
    }

}
