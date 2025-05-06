package com.fiveup.core.monitor.common;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fiveup.core.monitor.mapper.UserMapper2;
public class Datetime {
      public static String getdate() throws ParseException {
          SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date now = new Date();
          return sdf2.format(now);
    }

}