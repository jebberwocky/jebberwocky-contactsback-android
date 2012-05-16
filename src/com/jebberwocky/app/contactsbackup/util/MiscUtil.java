package com.jebberwocky.app.contactsbackup.util;

import android.text.format.Time;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: chihyuanliu
 * Date: 5/16/12
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtil {

    public static String formatFileName(String ext){
        String prefix ="contacts";
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Time now = new Time();
        now.setToNow();
        String r = prefix + now.format("yyyyMMddHHmm") + "."+ext;
        return r;
    }

}
