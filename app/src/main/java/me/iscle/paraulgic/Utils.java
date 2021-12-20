package me.iscle.paraulgic;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String getFormattedDate(boolean yesterday) {
        Calendar calendar = Calendar.getInstance();
        if (yesterday) {
            calendar.add(Calendar.DATE, -1);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }
}
