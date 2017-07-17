package com.mywx.utils;

/**
 * Created by wangzy on 2017/5/18.
 */
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


    public static String encodeMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes(Charset.forName("UTF-8")));
        byte[] hash = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                sb.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                sb.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return sb.toString();
    }
}
