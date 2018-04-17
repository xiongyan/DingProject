package com.project.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by laishun on 2018/4/3.
 */
public class DateUtil {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 获取当前时间
     * yyyy-MM-dd
     * @return
     */
    public static String getTodayTime(){
        Calendar cal = Calendar.getInstance();
        return format.format(cal.getTime());
    }

    /**
     * 获取当前时间
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTodayTimeStamp(){
        Calendar cal = Calendar.getInstance();
        return format2.format(cal.getTime());
    }

    /**
     * 获取当前时间
     * yyyyMMddHHmmss
     * @return
     */
    public static String getOrderIdByTimeStamp(){
        Calendar cal = Calendar.getInstance();
        return format3.format(cal.getTime());
    }

    /**
     * 获取一定间隔的时间
     * @param count
     * @return
     */
    public static String getOffDayTime(int count){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,count);
        return format.format(cal.getTime());
    }

    /**
     * 获取一定间隔的时间
     * @param count
     * @return
     */
    public static String getOffMonthTime(int count){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,count);
        return format.format(cal.getTime());
    }

    /**
     * 获取两个时间的月份间隔
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getIntervalMonth(String startTime ,String endTime){
        int res = 0;
        try {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(format.parse(startTime));
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(format.parse(endTime));
            res = endCal.get(Calendar.MONTH)-startCal.get(Calendar.MONTH);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
