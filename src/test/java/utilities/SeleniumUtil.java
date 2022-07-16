package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Set;

public class SeleniumUtil {

    public static boolean waitForElementToBeVisible(WebDriver driver, WebElement element, int seconds){
        try{
            WebDriverWait wait = new WebDriverWait(driver, seconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch (NoSuchElementException e){
            throw e;
        }
    }
}
