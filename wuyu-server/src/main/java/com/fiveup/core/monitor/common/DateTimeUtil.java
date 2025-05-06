package com.fiveup.core.monitor.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static Long parse(String startTime, String timeFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Date date = null;
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }

    public static String format(Date datetime, String retTimeFmt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(retTimeFmt);
        String date = simpleDateFormat.format(datetime);
        return Timestamp.valueOf(date).toString();
    }
    //时间戳转时间类型
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
    //时间类型转时间戳
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date.getTime();
    }
}
