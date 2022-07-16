package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportUtil extends BaseUtil{

    public static ExtentReports initializeExtentReport() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        extent = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-reports/extent-report-" +timeStamp+".html");
        reporter.config().setReportName("Workspace Test Report");
        extent.attachReporter(reporter);
        return extent;
    }
}
