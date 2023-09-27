package org.zooplus.common.utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.zooplus.config.ConfigReader;

import static com.codeborne.selenide.Selenide.open;

public class WebDriverClass {
    public static WebDriver driver;
   public static ConfigReader configReader = new ConfigReader();
    public  WebDriverClass(){
        driver = getDriver();
//        updateTheSIDCookie();
    }
    public static WebDriver getDriver(){
        setBrowserCapabilities();
        open(configReader.getProperty("url"));
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private static void setBrowserCapabilities(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-cache");
        chromeOptions.addArguments("--incognito");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        WebDriverRunner.setWebDriver(new ChromeDriver(chromeOptions));
    }
    public static void closeWebDriver() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
    }

    /** This one is working as required and the cookie gets updated by my name but for some reason when I enable it,
     * The new instance of the browser is opened cached so if there were some added products from earlier test case they
     * will be displayed and I am handling the cases assuming (which should be the case) a new instance will be opened.
     *
     * I want to know the solution if whomever reviewing this now knows :); PS: Tried clearing the cache but didnt work*/
    public static void updateTheSIDCookie(){
        Cookie cookie = new Cookie("sid", configReader.getProperty("sid"));
        driver.manage().addCookie(cookie);
    }
}
