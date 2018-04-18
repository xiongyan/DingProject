package com.project.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by laishun on 2018/3/12.
 */
@Component
public class Md5Util {

    /**
     * MD5
     * @param s
     * @return
     */
    public String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for(int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static void main(String[] args) throws  Exception{
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*Md5Util md = new Md5Util();
        System.out.print(md.MD5("123"));*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(format2.parse("2018-04-08 00:00:00"));
        System.out.println(cal.getTimeInMillis());

        cal.setTime(format2.parse("2018-04-08 23:59:59"));
        System.out.println(cal.getTimeInMillis());
    }

}
