package com.github.liufarui.dtParse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Package: com.github.liufarui.dt
 * @ClassName: DtParseSSH
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/3 11:20 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public class DtParseSSH {

    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init(String url, String user, String password) {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String check(String tables,
                               String cColumn,
                               String cColumnValue,
                               String jsonStr) throws SQLException, InterruptedException, ExecutionException {
        Map<String, Object> retMap = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<HashMap<String, Object>>> callableList = new ArrayList<>();
        for (String table : tables.trim().split(",")) {
            callableList.add(() -> {
                String sql = "select * from " + table + " where " + cColumn + "=\"" + cColumnValue + "\"";
                PreparedStatement st = conn.prepareStatement(sql);
                ResultSet resultSet = st.executeQuery();
                List<Map<String, Object>> retList = convertList(resultSet);
                if (retList.size() == 0) {
                    System.out.printf("Table:%s查询返回为空%n", table);
                } else if (retList.size() > 1) {
                    System.out.printf("Table:%s查询返回不唯一%n", table);
                } else {
                    return (HashMap<String, Object>) retList.get(0);
                }
                return new HashMap<>();
            });
        }
        List<Future<HashMap<String, Object>>> futureList = executorService.invokeAll(callableList);
        for (Future<HashMap<String, Object>> future : futureList) {
            retMap.putAll(future.get());
        }

        Map<String, Object> jsonMap = transToMap(
                jsonStr
                        .replace(" ", "")
                        .replace("\n", "")
        );

        StringBuilder sbNotExisted = new StringBuilder();
        StringBuilder sbFailed = new StringBuilder();

        assert jsonMap != null;
        jsonMap.forEach((key, value) -> {
            if (!retMap.containsKey(key)) {
                sbNotExisted.append(key).append(" 不存在！\n");
            } else if (!retMap.get(key).equals(value)) {
                sbFailed.append(key).append(" 比对失败！Json值：").append(value).append("，DB值：").append(retMap.get(key)).append("\n");
            }
        });

        return sbNotExisted.append(sbFailed).toString();
    }

    private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();

            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    private static Map<String, Object> transToMap(String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, Map.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
