package com.github.liufarui.dtParse.controller;

import com.github.liufarui.dtParse.service.UIService;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.sql.SQLException;

/**
 * @Package: com.github.liufarui.dt.controller
 * @ClassName: DtParseController
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/4 11:17 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public class DtParseController {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("数据比对小程序");
        frame.setSize(2000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UIService uiService = new UIService();
        JPanel panel = uiService.getPanel();
        frame.add(panel);

        frame.setVisible(true);
    }

}
