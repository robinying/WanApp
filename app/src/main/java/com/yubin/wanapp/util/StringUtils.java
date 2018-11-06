package com.yubin.wanapp.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class StringUtils {
    public static String hidePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return phone;
        }
        return phone.replaceAll("(?<=\\d{3})\\d(?=\\d{4})", "*");

    }

    public static String replaceInvalidChar(String text) {
        return text.replace("<em class='highlight'>", "")
                .replace("</em>", "")
                .replace("&mdash;", "-")
                .replace("&ndash;", "-")
                .replace("&ldquo;", "'")
                .replace("&rdquo;", "'")
                .replace("&amp;","&");
    }


    public static String hideName(String realname) {
        if (TextUtils.isEmpty(realname)) {
            return realname;
        }
        return realname.replaceAll(".$", "*");
//        StringBuilder builder = new StringBuilder();
//        if (realname.length() > 2) {
//            builder.append(realname.substring(0, 2)).append("*");
//        } else {
//            builder.append(realname.substring(0, 1)).append("*");
//        }
//
//        return builder.toString();
    }


    public static String hideBankCardNum(String val) {
        if (TextUtils.isEmpty(val)) {
            return "无卡号";
        }
        String res = "**** **** **** ";
        if (val.length() > 4) {
            res += val.substring(val.length() - 4, val.length());
        }

        return res;

    }

    public static String hideIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return idCard;
        }
        return idCard.replaceAll("(?<=\\d{2})\\d(?=\\d[\\dXx])", "*");

    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0 || "null".equals(str))
            return true;
        else
            return false;
    }

    public static String processShareUrl(String val) {
        if (TextUtils.isEmpty(val) || "--".equals(val)) {
            return "--  复制";
        }
        String[] split = val.split("/");
        if (val.length() > 22 && TextUtils.isEmpty(split[1])) {
            return split[0] + "//" + split[2] + "..." + split[split.length - 1] + " 复制";
        } else {
            return val + " 复制";
        }

    }
}
