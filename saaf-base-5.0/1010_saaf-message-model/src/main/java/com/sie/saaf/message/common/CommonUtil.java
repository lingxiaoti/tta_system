package com.sie.saaf.message.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chenzg
 * @createTime 2018-06-20 15:57
 * @description mail , phone ... validation
 */
public class CommonUtil {
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    public static boolean isMobileNum(String telNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

}
