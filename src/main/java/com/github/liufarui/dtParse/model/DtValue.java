package com.github.liufarui.dtParse.model;

import lombok.Data;

/**
 * @Package: com.github.liufarui.dt.model
 * @ClassName: DtValue
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/4 10:44 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
@Data
public class DtValue {
    private DtValue parent;
    private DtSource dtSource;
    private String value;

    public DtValue(DtSource dtSource, String value) {
        this.dtSource = dtSource;
        this.value = value;
    }

    public DtValue(DtSource dtSource, String value, DtValue parent) {
        this.parent = parent;
        this.dtSource = dtSource;
        this.value = value;
    }
}
