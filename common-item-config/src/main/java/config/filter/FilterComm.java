package config.filter;

import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 卫星
 * @package com.sskj.common.filter
 * @date 2018-12-13  09:38
 * @project C2C
 */
public class FilterComm {

    /**
     * 需要排除 xss 攻击 的字段
     */
    public static List<String> excludes = Lists.newArrayList("details", "content");

    /**
     * 需要 排除 xss 攻击的 url
     */
    public static List<String> excludesUrl = Lists.newArrayList();


    public static boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {

        if (excludes == null || excludes.isEmpty()) {
            return false;
        }

        String url = request.getServletPath();
        for (String pattern : excludesUrl) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }

}
