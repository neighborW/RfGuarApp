package com.rifeng.p2p.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {

    //把数据date型转化为String型
    public static String DateToString(Date date,String fm){
        SimpleDateFormat sf = new SimpleDateFormat(fm);
        return sf.format(date);
    }
}
