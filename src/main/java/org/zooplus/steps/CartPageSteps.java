package org.zooplus.steps;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.zooplus.customclasses.ProductClass;
import org.zooplus.pages.CartPage;

import org.assertj.core.api.SoftAssertions;

import java.util.*;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.zooplus.common.utils.BrowserInteractions.*;
import static org.zooplus.common.utils.BrowserWaits.*;
import static org.zooplus.common.utils.CommonUtils.decimalRounding;

public class CartPageSteps {
    CartPage cartPage =  new CartPage();
    SoftAssertions softAssertions = new SoftAssertions();
    List<WebElement> firstThreeElements = new ArrayList<>();

    private static final Logger LOGGER = LogManager.getLogger(CartPageSteps.class);


    public boolean checkEmptyShoppingCartIsDisplayed(){
        if(cartPage.getShoppingCartAreaElement().isDisplayed()){
            LOGGER.info("The shopping cart is empty");
            return true;
        }
        return false;
    }
    @Step("Verify that the shopping cart is empty")
    public void verifyThatShoppingCartIsEmpty(){
        step("Asserting that the shopping cart is empty");
        softAssertions.assertThat(cartPage.getShoppingCartAreaElement().isDisplayed()).as("Verify that the shopping cart is empty")
                .isEqualTo(true);
        step("Asserting that the correct text displayed showing shopping cart is empty");
        softAssertions.assertThat(cartPage.getEmptyCartText(cartPage.getEmptyShoppingCartText())).as("Verify that the text for empty shopping cart is correct")
                .isEqualTo("Your shopping basket is empty");
        softAssertions.assertAll();
    }

    @Step("Verifying that the URL contains 'cart' word")
    public void verifyThatUrlContainsCart(){
        softAssertions.assertThat(getBrowserUrl()).contains(cartPage.getCart());
        softAssertions.assertAll();
    }
    @Step("Getting the prices of the added products in descending order")
    public List<Float> getAddedProductsPricesListInDescendingOrder(){
        List<WebElement> addedProducts = cartPage.getAllAddedProductsContainers();
        List<Float> addedProductsPrices;
        if(!addedProducts.isEmpty()){
            addedProductsPrices = addedProducts.stream().map(element -> Float.parseFloat(cartPage.getSubTotalPriceForAProduct(element)
            )).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            return addedProductsPrices;
        }
        return null;
    }
    private List<ProductClass> getListOfProductsToBeSorted() {
        List<WebElement> addedProducts = cartPage.getAllAddedProductsContainers();

        if (!addedProducts.isEmpty()) {
            List<Float> addedProductsPrices = addedProducts.stream().map(element -> Float.parseFloat(cartPage.getSubTotalPriceForAProduct(element)
            )).collect(Collectors.toList());

            List<ProductClass> productsList = new ArrayList<>();
            for (int i = 0; i < addedProducts.size() && i < addedProductsPrices.size(); i++) {
                WebElement productElement = addedProducts.get(i);
                double price = addedProductsPrices.get(i);

                if (productElement != null) {
                    productsList.add(new ProductClass(productElement, price));
                } else {
                   LOGGER.error("Product is null");
                }
            }
            return productsList;
        }
        return null;
    }

    public List<ProductClass> getAddedProductsElementsListInDescendingOrder(){
        List<ProductClass> products = getListOfProductsToBeSorted();
        if(!products.isEmpty()){
            Collections.sort(products, Comparator.comparing(ProductClass::getProductPrice).reversed());
            return products;
        }
        LOGGER.error("Product list is empty");
        return null;
    }

    private List<ProductClass> getAddedProductsElementsListInAscendingOrder(){
        List<ProductClass> products = getListOfProductsToBeSorted();
        Collections.sort(products, Comparator.comparing(ProductClass::getProductPrice));
        return products;
    }

    private Map<String,Object> addItemToTheProductWithLowestPrice(){
        List<ProductClass> productListOrderedAscendingly = getAddedProductsElementsListInAscendingOrder();
        Map<String,Object> resultList = new HashMap<>();

        ProductClass productToBeIncremented = productListOrderedAscendingly.get(0);
        WebElement productElement = productToBeIncremented.getProductElement();
        int productQuantityBeforeIncrement = Integer.parseInt(productToBeIncremented.getProductElement().findElement(cartPage.getQuantityProductText()).getAttribute("value"));
        resultList.put("ProductElement",productToBeIncremented);
        resultList.put("ProductQuantity",productQuantityBeforeIncrement);
        cartPage.getAddProductButtonToCLick(productElement).click();
        return resultList;
    }

    public void verifyThatAnItemGotAddedToAProductInShoppingCart(){
        Map<String,Object> resultObject = addItemToTheProductWithLowestPrice();
        ProductClass productClass = (ProductClass) resultObject.get("ProductElement");
        int productQuantityBeforeIncrement = (int) resultObject.get("ProductQuantity");

        int productQuantityAfterIncrement = Integer.parseInt(productClass.getProductElement().findElement(cartPage.getQuantityProductText()).getAttribute("value"));
        double productPrice = productClass.getProductPrice();

        waitExactly(1000);
        double productPriceAfterAddingItems = Double.parseDouble(cartPage.getSubTotalPriceForAProduct(productClass.getProductElement()));

        step("Asserting that quantity increased by 1");
        softAssertions.assertThat(productQuantityAfterIncrement).isEqualTo(productQuantityBeforeIncrement+1);
        step("Asserting that total price for that product increased by checking product price * quantity");
        softAssertions.assertThat(productPriceAfterAddingItems).isEqualTo(decimalRounding(productPrice*productQuantityAfterIncrement));
        softAssertions.assertAll();
        LOGGER.info("User added an item to the lowest priced product");
    }

    @Step("Verifying that prices are ordered in descending order")
    public void verifyThatThePricesAreOrderedInDescendingOrder(){
        softAssertions.assertThat(getAddedProductsPricesListInDescendingOrder()).as("The list of the products' prices are sorted in descending order")
                .isSortedAccordingTo(Comparator.reverseOrder());
        softAssertions.assertAll();
        LOGGER.info("Products are order descendingly");
    }

    /* This method should handle 4 probabilities where I only handled two for the sake of the assignment and time
        Handled: Multiple products in shopping cart and deleting a product with ONLY one item from this product added
        and One product in the shopping cart and deleting this only item of this product.

        Unhandled: One product ONLY in the shopping cart with multiple items and another case, Multiple products added in
        the shopping cart and deleting and item from a product which has multiple item added from it
    */
    @Step("Deleting the product with highest price")
    public void verifyDeletionOfAProductInOrderedShoppingList(List<ProductClass> ordredProductsList){
        if(!ordredProductsList.isEmpty()){
            WebElement productActionElement = cartPage.getActionButtonsProduct(ordredProductsList.get(0).getProductElement());
            int quantityValueNumber = Integer.parseInt(productActionElement.findElement(cartPage.getQuantityProductText()).getAttribute("value"));

            if(ordredProductsList.size() > 1 && quantityValueNumber == 1){
                step("Deleting a product from the shopping cart while the shopping cart has multiple products added");
                productActionElement.findElement(cartPage.getDeleteProductButton()).click();
                step("Verifying that the product got deleted");
                verifyDeleteProductQuantityOneMultipleProductsCart(ordredProductsList.get(0),ordredProductsList.size());
            }
            if(ordredProductsList.size() == 1 && quantityValueNumber == 1){
                step("Deleting the only product with 1 item from the shopping cart");
                productActionElement.findElement(cartPage.getDeleteProductButton()).click();
                step("Verifying that the product got deleted");
                VerifyDeleteProductQuantityOneAndOneProductsInCart();
            }
        }
    }

    public void verifyDeleteProductQuantityOneMultipleProductsCart(ProductClass productToDelete,int orderedProductListSize){
       LOGGER.info("Size of the NEW ordered list after deletion: " + getAddedProductsElementsListInDescendingOrder().size());
       int productListSize = orderedProductListSize - 1;
       LOGGER.info("Size of the product list after deleting an item: " + productListSize);

       softAssertions.assertThat(getAddedProductsElementsListInDescendingOrder().size()).as("Size of the NEW ordered list after deletion")
                .isEqualTo(orderedProductListSize - 1);
       softAssertions.assertThat(productToDelete.getProductTitle()).as("Verify that the title for the deleted product is null").isNullOrEmpty();
       softAssertions.assertThat(cartPage.getAlertTextAfterDeletingProduct().getText()).as("Verify that the product got deleted")
                .contains("The item was successfully removed.");
       softAssertions.assertAll();
    }
    public void VerifyDeleteProductQuantityOneAndOneProductsInCart(){
        softAssertions.assertThat(cartPage.getEmptyShoppingContainer().isDisplayed())
                .as("Verify that the empty shopping cart is displayed").isEqualTo(true);
        softAssertions.assertThat(cartPage.getEmptyShoppingContainerAlert()).as("Verify the shopping cart empty alert")
                .contains("The item was successfully removed.");
        softAssertions.assertThat(cartPage.getEmptyShoppingContainerText()).as("Get the shopping cart empty text")
                .contains("Your shopping basket is empty");
        softAssertions.assertAll();
    }

    private void clickCountryLiBasedOnCountryName(String countryName){
        cartPage.getCountryNameLiBasedOnCountryName(countryName).click();
    }

    private void enterCountryCode(String postCode){
        cartPage.getPostCodeElement().sendKeys(postCode);
    }

   @Step("Getting the text for the country and post code after changing it to 'Portugal' and 5000")
    public String changeCountryAndPostCode(String countryName,String postCode){
        if(cartPage.getCartSummaryContainer().isDisplayed()){
            cartPage.getCountryShippingNameLink().click();
            if(cartPage.getShippingPopUpContainerElement().isDisplayed()){
                cartPage.getCountryDropdown().click();
                clickCountryLiBasedOnCountryName(countryName);
                enterCountryCode(postCode);
                /** This click either from keyboard or mouse is flaky, as the menu got hovered sometimes and focus on the
                 * button got lost but should be handled
                 * */
//                cartPage.getUpdateButton().sendKeys(Keys.ENTER);
//                putElementOnFocusAndClick(cartPage.getUpdateButton());
                cartPage.getUpdateButton().click();

            }
        }
        waitExactly(1000);
        return  cartPage.getCountryShippingNameText();
    }

    public void verifyThatCountryAndCodeChanged(String countryNameAndPostCode){
        step("Asserting that the country and postcode contains 'Portugal' and '5000'");
        softAssertions.assertThat(countryNameAndPostCode).contains("Portugal");
        softAssertions.assertThat(countryNameAndPostCode).contains("5000");
        softAssertions.assertAll();
        LOGGER.info("Country and Postcode are correct");
    }

    public double getSummationOfProductsPrices(){
        List<ProductClass> products = getListOfProductsToBeSorted();
        return decimalRounding(products.stream().mapToDouble(product -> product.getProductPrice()).sum());
    }
    public double getTotalAmount(){
        double totalAmount = (getSummationOfProductsPrices() + cartPage.getShippingCostAmount());
        return  decimalRounding(totalAmount);
    }
    public void verifyThatSubTotalIsCorrect() {
        step("Asserting that the subtotal amount equals to summation of ALL the products' prices");
        softAssertions.assertThat(cartPage.getSubToTalText()).as("Verify that Subtotal amount is equal to the summation of products' prices")
                .isEqualTo(getSummationOfProductsPrices());
        softAssertions.assertAll();
        LOGGER.info("The subtotal amount is correct");
    }
    public void verifyThatTotalAmountIsCorrect(){
        step("Asserting that total amount is equal to ALL prices + shipping fees");
        softAssertions.assertThat(cartPage.getTotalAmountNumber())
                .as("Verify that Total amount is equal to the summation of products' prices + shipping fees")
                .isEqualTo(getTotalAmount());
        softAssertions.assertAll();
        LOGGER.info("Total amount for the shopping cart is correct");
    }

    private List<WebElement> getFewProductsToAddThem(int limit){
        List<WebElement> productsLis = cartPage.getProductsLis();
        firstThreeElements = productsLis
                .stream()
                .distinct()
                .limit(limit)
                .collect(Collectors.toList());
        return firstThreeElements;
    }

    public boolean addMultipleProductsToShoppingCart(int limit) {
        if(limit > cartPage.getProductsLis().size())
        {
            LOGGER.info("Number of products provided is larger than the size of the elements");
            return false;
        }
        for (int i = 0; i < limit; i++) {
            try {
                scrollToElement(findElementByLocator(cartPage.getRecommendedContainer()));
                waitExactly(1000);
                WebElement buttonToClick = getFewProductsToAddThem(limit).get(0).findElement(By.tagName("button"));
                waitForElementToBeClickable(buttonToClick);
                buttonToClick.click();
//                click(buttonToClick);
                waitExactly(1000);
            } catch (StaleElementReferenceException e) {
                e.getMessage();
            }
        }
        return true;
    }
    public void verifyThatProductsGotAddedToShoppingCart(){
        waitExactly(1000);
        softAssertions.assertThat(cartPage.getAllAddedProductsContainers().size()).isEqualTo(firstThreeElements.size());
        softAssertions.assertThat(cartPage.getEmptyCartText(cartPage.getShoppingCartText())).as("Assert that the shopping cart title is equal to: ")
                .contains("Your Shopping Basket");
        softAssertions.assertAll();
    }

        public void addProductsToTheShoppingCartAndProceed(){
        int productNumberToAdd = 1;

        if(checkEmptyShoppingCartIsDisplayed()){
            addMultipleProductsToShoppingCart(productNumberToAdd);
        }
        LOGGER.info("Adding products to the cart until user reaches the minmum amount to proceed checking out");
        while(cartPage.getSubToTalText() < cartPage.getMinimumAmount())
        {addMultipleProductsToShoppingCart(productNumberToAdd);}
        cartPage.getActiveProceedButton().click();
    }
    public void verifyThatUserCheckedOutOrder(){
        step("Asserting that the URL no more containing 'Cart'");
        softAssertions.assertThat(getBrowserUrl()).doesNotContain(cartPage.getCart());
        softAssertions.assertAll();
        LOGGER.info("User can proceed checking out his cart");
    }

}



