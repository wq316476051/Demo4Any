package com.wang.demo4any;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTest {
    
    @Test
    public void simple(){
        SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS ");
        String format = DATE_FORMATTER.format(new Date());
        System.out.println("date = " + format);
    }
}
