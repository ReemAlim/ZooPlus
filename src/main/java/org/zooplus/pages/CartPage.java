package org.zooplus.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.zooplus.common.utils.WebDriverClass;

import java.util.List;

import static org.zooplus.common.utils.BrowserInteractions.*;
import static org.zooplus.common.utils.BrowserWaits.*;
import static org.zooplus.common.utils.CommonUtils.decimalRounding;

@Getter
public class CartPage extends WebDriverClass {
    public CartPage(){super();}

    private final By shoppingCartArea = By.cssSelector("[data-testid='checkout-empty-cart']");
    private final By emptyShoppingCartElement = By.cssSelector("[data-zta='H1UIC']");
    private final String emptyShoppingCartText = "Your shopping basket is empty";
    private final String ShoppingCartText = "Your Shopping Basket";

    private final By initialRecommendationBox = By.id("splide01");
    private final By recommendationBox = By.id("splide03");
    private final By productsInitialUlId = By.id("splide01-list");
    private final By productsUlId = By.id("splide03-list");
    private  final By recommendedContainer = By.xpath("//h2[contains(., 'recommend')]//following-sibling::div//ul//li");


    // Check Cart in URL locators
    private String cart = "cart";

    // Check products' prices in descending order locators
    private final By productPrice = By.cssSelector("div[data-zta='articleQuantitySubtotal'] span[data-zta='productStandardPriceAmount']");
    private final By addedProductsContainers = By.cssSelector("div[data-zta='FlatBoxUIC'] article[data-zta='standard_article']");
    // Locators for deleting products
    private final By actionButtonsContainer = By.cssSelector("div[data-zta='QuantityStepperUIC']");
    private final By deleteProductButton = By.cssSelector("button[data-zta='quantityStepperDecrementButton']");
    private final By quantityProductText = By.cssSelector("input[data-zta='quantityStepperInput']");
//    private final By productTitleText = By.cssSelector("article[data-zta='standard_article'] article[data-zta='productInfo']  a[data-zta='productName']");
    private final By deleteAlertContainer = By.cssSelector("div[data-zta='reAddArticleAlert']");
    private final By checkoutEmptyContainer = By.cssSelector("section[data-testid='checkout-empty-cart']");
    private final By emptyContainerTextLocator = By.cssSelector("section[data-testid='checkout-empty-cart'] h1[data-zta='H1UIC']");
    private final By emptyContainerAlert = By.cssSelector("section[data-testid='checkout-empty-cart'] div[data-zta='alertText'] p[data-zta='P1UIC']");
    private final By addProductButton = By.cssSelector("button[data-zta='quantityStepperIncrementButton']");

    // Locators for cart summary
    private final By cartSummary = By.id("cartSummary");
    private final By shippingCountryNameAndPostCode = By.cssSelector("a[data-zta='shippingCountryName']");
    private final By shippingPopUpContainer = By.cssSelector("div[data-zta='bpaPopoverTemplate']");
    private final By countryDivContainer = By.cssSelector("div[data-zta='shippingCostDropdown']");
    private final String countryLi = "div[data-zta='dropdownMenuMenu'] ul li[data-label='countryName']";
    private final By postCodeInput = By.cssSelector("div[data-zta='shippingCostZipcode'] input[placeholder='Postcode']");
    private final By shippingPopupUpdateButton = By.cssSelector("div[data-zta='bpaPopoverTemplate'] button[data-zta='shippingCostPopoverAction']");
    private final By subtotalValue = By.cssSelector("p[data-zta='overviewSubTotalValue']");
    private final By shippingCostValue = By.cssSelector("p[data-zta='shippingCostValueOverview']");
    private final By totalAmount = By.cssSelector("h3[data-zta='total__price__value']");

    private final By minimumContainerAlert = By.cssSelector("div[id='cartSummary'] div[data-zta='alertText'] p[data-zta='P1UIC']");
    private final By activeProceedButtonLocator = By.cssSelector("button[data-zta='gotoPreviewBottom']");
    private final By disabledProceedButtonLocator = By.cssSelector("button[disabled]");
    private final double minimumAmount =19.00;

    public WebElement getShoppingCartAreaElement(){
        waitForElementPresent(getShoppingCartArea());
        return findElementByLocator(getEmptyShoppingCartElement());
    }

    public String getEmptyCartText(String text){
        waitForTextPresent(getEmptyShoppingCartElement(),text);
        return text;
    }

    public String getSubTotalPriceForAProduct(WebElement element){
        waitForElementVisible(element);
        return element.findElement(getProductPrice()).getText().substring(1);
    }

    public List<WebElement> getAllAddedProductsContainers() {
        waitExactly(1000);
        waitForElementsVisible(getAddedProductsContainers());
        return findElementsByLocator(getAddedProductsContainers());
    }

    public WebElement getAlertTextAfterDeletingProduct(){
        waitForElementsVisible(getDeleteAlertContainer());
        return findElementByLocator(getDeleteAlertContainer());
    }

    public String getEmptyShoppingContainerText(){
        waitForElementsVisible(getEmptyContainerTextLocator());
        return findElementByLocator(getEmptyContainerTextLocator()).getText();
    }
    public String getEmptyShoppingContainerAlert(){
        waitForElementsVisible(getEmptyContainerAlert());
        return findElementByLocator(getEmptyContainerAlert()).getText();
    }

    public WebElement getEmptyShoppingContainer(){
        waitForElementsPresent(getCheckoutEmptyContainer());
        return findElementByLocator(getCheckoutEmptyContainer());
    }

    public WebElement getActionButtonsProduct(WebElement element){
        waitForElementVisibleByLocator(getActionButtonsContainer());
        return element.findElement(getActionButtonsContainer());
    }

    public WebElement getAddProductButtonToCLick(WebElement element){
        waitForElementClickable(getAddProductButton());
        return element.findElement(getAddProductButton());
    }

    public String getCountryShippingNameText(){
        waitForElementVisibleRefreshByLocator(getShippingCountryNameAndPostCode());
        return findElementByLocator(getShippingCountryNameAndPostCode()).getText();
    }
    public WebElement getCountryShippingNameLink(){
        waitForElementVisibleByLocator(getShippingCountryNameAndPostCode());
        return findElementByLocator(getShippingCountryNameAndPostCode());
    }
    public WebElement getCartSummaryContainer(){
        waitForElementsVisible(getCartSummary());
        return findElementByLocator(getCartSummary());
    }

    public WebElement getShippingPopUpContainerElement(){
        waitForElementsPresent(getShippingPopUpContainer());
        return findElementByLocator(getShippingPopUpContainer());
    }

    public WebElement getCountryDropdown(){
        waitForElementsPresent(getCountryDivContainer());
        return findElementByLocator(getCountryDivContainer());
    }

    public WebElement getCountryNameLiBasedOnCountryName(String country){
        String countryString = getCountryLi().replace("countryName",country);
        waitForElementVisibleByLocator(By.cssSelector(countryString));
        return findElementByLocator(By.cssSelector(countryString));
    }

    public WebElement getPostCodeElement(){
        waitForElementPresent(getPostCodeInput());
        return findElementByLocator(getPostCodeInput());
    }
    public WebElement getUpdateButton(){
        waitForElementClickable(getShippingPopupUpdateButton());
        return findElementByLocator(getShippingPopupUpdateButton());
    }

    public double getSubToTalText(){
        waitForElementPresent(getSubtotalValue());
        return  Double.parseDouble(findElementByLocator(getSubtotalValue()).getText().substring(1));
    }

    public double getTotalAmountNumber(){
        waitForElementPresent(getTotalAmount());
        return decimalRounding(Double.parseDouble(findElementByLocator(getTotalAmount()).getText().substring(1)));
    }
    public double getShippingCostAmount(){
        waitForElementPresent(getShippingCostValue());
        return Double.parseDouble(findElementByLocator(getShippingCostValue()).getText().substring(1));
    }
    public List<WebElement> getProductsLis(){
        waitForElementsPresent(getRecommendedContainer());
        return findElementsByLocator(getRecommendedContainer());
    }

    public WebElement getMinimumAlert(){
        waitForElementPresent(getMinimumContainerAlert());
        return findElementByLocator(getMinimumContainerAlert());
    }

    public String getMinimumAlertText(){
        waitForElementPresent(getMinimumContainerAlert());
        return findElementByLocator(getMinimumContainerAlert()).getText();
    }
    public WebElement getActiveProceedButton(){
        waitForElementClickable(getActiveProceedButtonLocator());
        return findElementByLocator(getActiveProceedButtonLocator());
    }
    public WebElement getDisabledProceedButton(){
        waitForElementPresent(getDisabledProceedButtonLocator());
        return findElementByLocator(getDisabledProceedButtonLocator());
    }
}
