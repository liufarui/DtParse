package com.github.liufarui.dtParse.util;

import java.util.regex.Pattern;

/**
 * @Package: com.github.liufarui.dt.util
 * @ClassName: StringUtils
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/5 12:33 上午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public class StringUtils {
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
}
