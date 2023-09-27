package org.zooplus.common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import static org.zooplus.common.utils.WebDriverClass.driver;
import static org.zooplus.common.utils.WebDriverClass.getDriver;

public class BrowserInteractions {
    private static JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

    public static void click(WebElement element){
        element.click();
    }

    public static WebElement findElementByLocator(By by){
        return driver.findElement(by);
    }

    public static List<WebElement> findElementsByLocator(By by){
        return  driver.findElements(by);
    }

    public static void scrollToElement(WebElement elementToScrollTo) {
        jsExecutor.executeScript("arguments[0].scrollIntoView();", elementToScrollTo);
    }

    public static void scrollToTheTopOfThePage() {
        jsExecutor.executeScript("window.scrollTo(0, 0)");
    }

    public static String getBrowserUrl(){
        return driver.getCurrentUrl();
    }
    public static void putElementOnFocusAndClick(WebElement element){
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).perform();
        element.click();
    }
}
