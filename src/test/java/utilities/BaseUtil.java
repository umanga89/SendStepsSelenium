package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;

import java.net.URL;
import java.util.Properties;

public class BaseUtil {

    //public WebDriver driver;
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    //public static WebDriver driver;
    public static String webUrl;
    public static String base_directory;
    public static Properties configProperties;
    public static ThreadLocal<ExtentTest> extenttest = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentTest> extentsuite = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentReports> extentreport = new ThreadLocal<ExtentReports>();
    public static ExtentReports extent;
    public static ExtentTest suite;
    public static ExtentTest test;

    @BeforeSuite
    public void startDocker() throws Exception {
        try {
            System.out.println("Executing before suite===================================================");
            TerminalCommandsUtil.runTerminalCommand("docker compose up", "Node has been added");
        }catch (Exception e){
            throw e;
        }
    }

    @AfterSuite
    public void stopDocker() throws Exception {
        TerminalCommandsUtil.runTerminalCommand("docker compose down", "cancelled");
    }

    @BeforeMethod
    @Parameters("browser")
    public void launchBrowser(String browser) throws Exception{
        try{
            webUrl = configProperties.getProperty("web.url");
            setDriver(browser);
            driver.get().get(webUrl);
        }catch(Exception e){
            throw e;
        }
    }

    @AfterMethod
    public void closeBrowser(){
        LogUtil.info("Test completed!");
        driver.get().quit();
    }

    public WebDriver getDriver(){return driver.get();}

    public void setDriver(String browser) throws Exception {
        try {
            if (browser != null) {
                DesiredCapabilities cap = new DesiredCapabilities();
                switch (browser) {
                    case "chrome":
                        //System.setProperty("webdriver.chrome.driver", base_directory + configProperties.getProperty("chrome.driver.path"));
                        WebDriverManager.chromedriver().setup();
//                        //disable chrome notifications
//                        ChromeOptions ops = new ChromeOptions();
//                        ops.addArguments("--disable-notifications");
//                        driver.set(new ChromeDriver(ops));
//                        break;
                        cap = DesiredCapabilities.chrome();
                        //cap.setPlatform(Platform.LINUX);
                        //driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap));
                        break;
//                    case "safari":
//                        driver.set(new SafariDriver());
//                        break;
                    case "firefox":
                        //System.setProperty("webdriver.gecko.driver", base_directory + configProperties.getProperty("firefox.driver.path"));
                        WebDriverManager.firefoxdriver().setup();
                        cap = DesiredCapabilities.firefox();
                        cap.setPlatform(Platform.LINUX);
                        //driver.set(new RemoteWebDriver(new URL("http://127.0.0.1:4446/wd/hub"), capFF));
                        break;
                    case "":
                        throw new Exception("browser parameter should be either \"chrome \\firefox\" in testng.xml");
                }
                driver.set(new RemoteWebDriver(new URL("http://127.0.0.1:4445"), cap));
                driver.get().manage().window().maximize();
            }else{
                throw new Exception("browser parameter is not configured in testng.xml");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
