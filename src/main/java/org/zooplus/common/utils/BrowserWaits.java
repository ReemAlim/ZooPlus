package org.zooplus.common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.zooplus.common.utils.WebDriverClass.driver;

public class BrowserWaits {
    private static final long TIME_OUT_IN_SECONDS_DEFAULT = 1000;

    private BrowserWaits() {
    }

    public static void waitForElementPresent(By locator) {
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForElementClickable(By by) {
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.elementToBeClickable(by));
    }
    public static void waitForElementVisible(WebElement element) {
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementVisibleByLocator(By locator) {
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static void waitForElementVisibleRefreshByLocator(By locator) {
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
    }
    public static void waitForElementsPresent(By locator){
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
    public static void waitForElementsVisible(By locator){
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    public static void waitForTextPresent(By locator, String textToBePresent){
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.textToBePresentInElementLocated(locator, textToBePresent));
    }
    public static void waitForElementToBeClickable(WebElement element){
        waitFor(TIME_OUT_IN_SECONDS_DEFAULT).until(ExpectedConditions.elementToBeClickable(element));
    }
    private static WebDriverWait waitFor(long timeOutInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
    }
    public static void waitExactly(long timeOutInMilliseconds) {
        try {
            Thread.sleep(timeOutInMilliseconds);
        } catch (InterruptedException e) {
           e.getMessage();
        }
    }
}
