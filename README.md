
# ZooPlus Automation Task

This task covers 10 test cases for the 'Shopping Cart' page; They mainy cover adding, deleting and sorting the shopping cart list

## Table of Contents:

- [Installation](#installation)
- [Test cases covered](#tc)
- [Notes](#notes)

## Installation:

- Clone the project locally 
- Run mvn clean install
  
For running the tests, you will need to run every and each test individually as for some reason couldnt set the TestNG xml nor was abeld to run the whole class (will work on this part :))
Tests will be found under **ZooPlus/src/test/java/tests/CartPageTests.java**

To run Allure report after running the tests, please run these two commands:
**allure generate allure-results --clean
allure serve allure-report**

## Test cases covered are:
- Deleting one of the items from a product with highest price
- Adding an item to the product with lowest price
- Checking the URL contains 'Cart'
- Checking that user can proceed when his cart has products' total amount (without the shipping fees) more than 19 
- Adding products to the list based on the user input (number of products he wants to add)
- Changing the country and postcode to 'Portugal' and '5000'
- Checking on the total amount correcness
- Checking on the subtotal amount correcness
- Arranging the product list ascendingly
- CHeck that the shopping cart is empty

## Notes:
- For the assignment, I only ran it on chrome so this is the only browser covered
- I covered the SID as required but for a reason I dont know (never had it before) when this cookie is updated, the browser is opened cached, I tried to figure it out but for the current situation I commented it;
  You can just uncommented it and run but the behavior will be messed up because of the already aded products from the previous run



