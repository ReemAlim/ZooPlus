package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.zooplus.pages.BaseClass;
import org.zooplus.steps.CartPageSteps;

import static io.qameta.allure.Allure.step;
import static org.zooplus.common.utils.BrowserInteractions.scrollToTheTopOfThePage;
import static org.zooplus.common.utils.WebDriverClass.*;

public class CartPageTests{

    BaseClass baseClass = new BaseClass();
    CartPageSteps cartPageSteps = new CartPageSteps();
    private static boolean limitCondition() {
        return true;
    }
    @BeforeEach
    public void setup(){
        baseClass.clickAgreeAndContinueBtn();
    }
    @Description("Test to check the shopping cart is empty before user adds any product")
    @DisplayName("Check that the shopping cart is empty")
    @Test
    public void checkTheShoppingCartIsEmpty(){
        step("Clicking 'Agree and Accept' cookies");
        baseClass.clickAgreeAndContinueBtn();
        step("Verifying that the shopping cart is empty");
        cartPageSteps.verifyThatShoppingCartIsEmpty();
    }

    @ParameterizedTest
    @CsvSource(value = {"2"})
    @Description("Verifying that user can add multiple products to the shopping cart based on his input")
    @DisplayName("Check that the user can add multiple products to the shopping cart")
    public void addMultipleProductsToTheShoppingCart(int numberOfProducts){
        addingProductsToShoppingCart(numberOfProducts);
    }

    @Description("Test to verify that the URL contains 'cart'")
    @DisplayName("Check that the URL contains 'cart'")
    @Test
    public void checkThatTheURLContainsCart(){
        cartPageSteps.verifyThatUrlContainsCart();
    }

    @Description("Test to verify that products are ordered descendingly")
    @DisplayName("Check that the product are in descending order")
    @ParameterizedTest
    @CsvSource(value = {"2"})
    public void arrangeTheProductsPriceInDescendingOrder(int numberOfProducts){
        step("Adding products to the shopping cart");
        addingProductsToShoppingCart(numberOfProducts);
        cartPageSteps.verifyThatThePricesAreOrderedInDescendingOrder();
    }

    @Description("Test to verify that the the highest price product gets deleted")
    @DisplayName("Check that the highest price product gets deleted")
    @ParameterizedTest
    @CsvSource(value = {"4"})
    @Step("Deleting the highest priced product")
    public void checkThatUserCanDeleteAProductWithHighestPrice(int numberOfProducts){
        step("Adding products to the shopping cart");
        addingProductsToShoppingCart(numberOfProducts);
        scrollToTheTopOfThePage();
        step("Verifying that prices are ordered in descending order");
        cartPageSteps.verifyThatThePricesAreOrderedInDescendingOrder();
        step("Deleting product with highest price");
        cartPageSteps.verifyDeletionOfAProductInOrderedShoppingList(cartPageSteps.getAddedProductsElementsListInDescendingOrder());
    }

    @Description("Test to verify that user can add an item to an existing product in the shopping cart with lowest price")
    @DisplayName("Check that the user can add item to a product with the lowest price")
    @ParameterizedTest
    @CsvSource(value={"3"})
    public void checkThatUserCanAddItemToAProductWithLowestPrice(int numberOfProducts){
        step("Adding products to the shopping cart");
        addingProductsToShoppingCart(numberOfProducts);
        scrollToTheTopOfThePage();
        cartPageSteps.verifyThatAnItemGotAddedToAProductInShoppingCart();
    }

    @Description("Test to verify that user can change the shipping country/code")
    @DisplayName("Check that the user can change the shipping country/code")
    @ParameterizedTest
    @CsvSource(value = {"Portugal,5000,3"})
    public void checkThatUserCanChangeTheShippingCountry(String country,String postCode,int numberOFProducts){
        step("Adding products to the shopping cart");
        addingProductsToShoppingCart(numberOFProducts);
        String countryNameAndPostCode = cartPageSteps.changeCountryAndPostCode(country, postCode);
        step("Checking that the country and postcode are equal 'Portugal' and '5000'");
        cartPageSteps.verifyThatCountryAndCodeChanged(countryNameAndPostCode);
    }

    @Description("Checking that the products' subtotal amount equals to ALL the products' prices")
    @DisplayName("Ccheck that the products' subtotal amount is correct")
    @ParameterizedTest
    @CsvSource(value = {"2"})
    public void checkThatSubTotalAmountIsCorrect(int numberOfProducts){
        step("Adding products to the shopping cart");
        addingProductsToShoppingCart(numberOfProducts);
        cartPageSteps.verifyThatSubTotalIsCorrect();
    }

    @Description("Checking that the products' total amount equal to subtotal and shipping fees together")
    @DisplayName("Check that the total amount for the shopping cart is correct")
    @ParameterizedTest
    @CsvSource(value = {"3"})
    public void checkThatTotalAmountIsCorrect(int numberOfProducts){
        step("Adding products to the shopping cart");
        addingProductsToShoppingCart(numberOfProducts);
        cartPageSteps.verifyThatTotalAmountIsCorrect();
}
    @Test
    @Description("Checking that user can checkout when total price (without shipping fee) > 19")
    @DisplayName("Check that the user can proceed checking out his cart")
    public void checkThatUserCanCheckOutHisOrder(){
        step("Clicking 'Agree and Accept' cookies");
        baseClass.clickAgreeAndContinueBtn();
        step("Adding products to the shopping cart to proceed with the purchase");
        cartPageSteps.addProductsToTheShoppingCartAndProceed();
        cartPageSteps.verifyThatUserCheckedOutOrder();

    }
    private void addingProductsToShoppingCart(int numberOfProducts){
        step("Clicking 'Agree and Accept' cookies");
        baseClass.clickAgreeAndContinueBtn();
        if (limitCondition()) {
            if (cartPageSteps.addMultipleProductsToShoppingCart(numberOfProducts)) {
                cartPageSteps.verifyThatProductsGotAddedToShoppingCart();
            }
        }
    }
    @AfterEach
    public void teardown() {
        closeWebDriver();
    }
}
