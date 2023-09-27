package org.zooplus.customclasses;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebElement;

@Getter
@Setter
public class ProductClass {

    private  WebElement productElement;
    private  double productPrice;
    private  String productTitle = null;

    public ProductClass(){}

    public ProductClass(WebElement productElement, Double productPrice) {
        this.productElement = productElement;
        this.productPrice = productPrice;
    }
}
