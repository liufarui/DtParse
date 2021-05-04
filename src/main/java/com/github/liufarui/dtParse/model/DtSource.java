package com.github.liufarui.dtParse.model;

/**
 * @Package: com.github.liufarui.dt.model
 * @ClassName: DtSource
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/4 10:45 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public enum DtSource {
    DB(0, "数据库"),
    JSON(1, "Json串");

    private int code;
    private String desc;

    DtSource(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "DtSource{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
