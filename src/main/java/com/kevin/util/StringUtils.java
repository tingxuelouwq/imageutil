package com.kevin.util;

/**
 * @类名：StringUtils
 * @包名：com.kevin.util
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/7/7 10:33
 * @版本：1.0
 * @描述：字符串操工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否为空，null或者空字符串时返回true，其他情况返回false
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
