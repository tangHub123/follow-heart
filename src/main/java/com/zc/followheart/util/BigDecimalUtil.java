package com.zc.followheart.util;




import com.alibaba.excel.util.StringUtils;

import java.math.BigDecimal;

public class BigDecimalUtil {

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1,String v2){
        if(StringUtils.isBlank(v1) || StringUtils.isBlank(v2)){
            return "-";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    public static void main(String[] args) {
        //Original String
        String string = "hello world";

        //Convert to byte[]
        byte[] bytes = {117, 115, 101, 114, 109, 97, 110, 97, 103, 101, 114, 49, 95, 49, 54, 56, 50, 53, 54, 50, 56, 48, 52, 53, 55, 53};



        //Convert back to String
        String s = new String(bytes);

        //Check converted string against original String
        System.out.println("Decoded String : " + s);

    }

}
