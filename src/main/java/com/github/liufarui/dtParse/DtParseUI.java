package com.github.liufarui.dtParse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.sql.SQLException;

/**
 * @Package: com.github.liufarui.dt
 * @ClassName: DtParseUI
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/2 11:49 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public class DtParseUI {

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("数据比对小程序");
        frame.setSize(2000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) throws SQLException {
        panel.setLayout(null);

        JLabel urlLabel = new JLabel("URL:");
        urlLabel.setBounds(10, 20, 80, 40);
        panel.add(urlLabel);

        JTextField url = new JTextField("mysql-cn-north-1-f43a6fe74d8e4ae7.rds.jdcloud.com", 20);
        url.setBounds(70, 20, 390, 40);
        panel.add(url);

        JLabel portLabel = new JLabel("port:");
        portLabel.setBounds(10, 80, 80, 40);
        panel.add(portLabel);

        JTextField port = new JTextField("3358", 20);
        port.setBounds(70, 80, 150, 40);
        panel.add(port);

        JLabel dbLabel = new JLabel("DB:");
        dbLabel.setBounds(250, 80, 80, 40);
        panel.add(dbLabel);

        JTextField db = new JTextField("cp_order", 20);
        db.setBounds(310, 80, 150, 40);
        panel.add(db);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(10, 140, 80, 40);
        panel.add(userLabel);

        JTextField user = new JTextField("adminwl", 20);
        user.setBounds(70, 140, 150, 40);
        panel.add(user);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(250, 140, 80, 40);
        panel.add(passwordLabel);

        JTextField password = new JTextField("2yfTkySnW6EcD", 20);
        password.setBounds(310, 140, 150, 40);
        panel.add(password);

        JLabel tablesLabel = new JLabel("表:");
        tablesLabel.setBounds(10, 200, 80, 40);
        panel.add(tablesLabel);

        JTextField tables = new JTextField("t_order_attachment", 20);
        tables.setBounds(70, 200, 1000, 40);
        panel.add(tables);

        JLabel cColumnLabel = new JLabel("主键:");
        cColumnLabel.setBounds(10, 260, 80, 40);
        panel.add(cColumnLabel);

        JTextField cColumn = new JTextField("order_no", 20);
        cColumn.setBounds(70, 260, 150, 40);
        panel.add(cColumn);

        JLabel cColumnValueLabel = new JLabel("主键值:");
        cColumnValueLabel.setBounds(250, 260, 80, 40);
        panel.add(cColumnValueLabel);

        JTextField cColumnValue = new JTextField("EC0010000000546", 20);
        cColumnValue.setBounds(310, 260, 150, 40);
        panel.add(cColumnValue);

        JLabel jsonStrLanel = new JLabel("Json串:");
        jsonStrLanel.setBounds(10, 320, 80, 40);
        panel.add(jsonStrLanel);

        JTextArea jsonStr = new JTextArea("{\"address\":0,\"attachment_name\":\"aa\",\"order_no\":\"EC0010000000546\",\"ref_id\":\"100092017300\"}");
        // 激活自动换行功能
        jsonStr.setLineWrap(true);
        // 激活断行不断字功能
        jsonStr.setWrapStyleWord(true);

        JScrollPane js = new JScrollPane(jsonStr);
        // 分别设置水平和垂直滚动条总是出现
        // js.setHorizontalScrollBarPolicy(
        //         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        js.setBounds(70, 320, 1000, 200);
        panel.add(js);

        JButton loginButton = new JButton("计算");
        loginButton.setBounds(10, 530, 80, 40);
        loginButton.doClick();

        loginButton.addActionListener(login -> {
            try {
                String dbUrl = "jdbc:mysql://" + url.getText() + ":" + port.getText() + "/" + db.getText();
                DtParse.init(dbUrl, user.getText(), password.getText());
                String checkValue = DtParse.check(tables.getText(), cColumn.getText(), cColumnValue.getText(), jsonStr.getText());
                System.out.println(checkValue);
                JOptionPane.showMessageDialog(null, checkValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        panel.add(loginButton);
    }
}
