package com.zc.followheart.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Long getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return Long.valueOf(dateFormat.format(new Date()));
    }
}
