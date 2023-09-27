package org.zooplus.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import static org.zooplus.common.utils.BrowserInteractions.findElementByLocator;
import static org.zooplus.common.utils.BrowserWaits.waitForElementClickable;

@Getter
public class BaseClass {
    private final By cookieBox = By.id("onetrust-banner-sdk");
    private  final By agreeAndContinueBtn = By.id("onetrust-accept-btn-handler");
    public BaseClass(){}

    @Step("Accepting the cookie popup")
    public  void clickAgreeAndContinueBtn(){
        waitForElementClickable(getAgreeAndContinueBtn());
        findElementByLocator(getAgreeAndContinueBtn()).click();
        }
}