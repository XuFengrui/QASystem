package com.qa.system.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName FormatChange
 * @Description DOTO
 * @Author XuFengrui
 * @date 2020/4/16
 * @Version 1.0
 **/
@Component
public class FormatChange {

    /**
    * @Author XuFengrui
    * @Description
    * @Date 12:13 2020/3/30
    * @Param [date]
    * @return java.lang.String
    **/
    public static String dateTimeChange(Date date){
        String strDate="";
        if(date != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strDate=format.format(date);
        }
        return strDate;
    }
}
