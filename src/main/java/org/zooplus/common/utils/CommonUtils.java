package org.zooplus.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonUtils {

    public CommonUtils(){}
    public static  double decimalRounding(double doubleNumber){
        BigDecimal bd = new BigDecimal(doubleNumber).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
