package com.github.liufarui.dtParse.dao;

import com.github.liufarui.dtParse.model.DtSource;
import com.github.liufarui.dtParse.model.DtValue;

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
 * @Package: com.github.liufarui.dt.dao
 * @ClassName: SqlSelect
 * @Description:
 * @Author: liufarui
 * @CreateDate: 2021/5/4 11:01 下午
 * @Copyright: Copyright (c)2021 JDL.CN All Right Reserved
 * @Since: JDK 1.8
 * @Version: V1.0
 */
public class SqlSelect {
    private Connection conn;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SqlSelect(String url, String user, String password) {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, DtValue> getDataMap(String tables,
                                           String cColumn,
                                           String cColumnValue) throws SQLException, InterruptedException, ExecutionException {
        Map<String, DtValue> retMap = new HashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<HashMap<String, DtValue>>> callableList = new ArrayList<>();
        for (String table : tables.trim().split(",")) {
            callableList.add(() -> {
                String sql = "select * from " + table + " where " + cColumn + "=\"" + cColumnValue + "\"";
                PreparedStatement st = conn.prepareStatement(sql);
                ResultSet resultSet = st.executeQuery();
                List<Map<String, DtValue>> retList = convertList(resultSet, table);
                if (retList.size() == 0) {
                    System.out.printf("Table:%s查询返回为空%n", table);
                } else if (retList.size() > 1) {
                    System.out.printf("Table:%s查询返回不唯一%n", table);
                } else {
                    return (HashMap<String, DtValue>) retList.get(0);
                }
                return new HashMap<>();
            });
        }
        List<Future<HashMap<String, DtValue>>> futureList = executorService.invokeAll(callableList);
        for (Future<HashMap<String, DtValue>> future : futureList) {
            retMap.putAll(future.get());
        }

        return retMap;
    }

    private List<Map<String, DtValue>> convertList(ResultSet rs, String table) throws SQLException {
        List<Map<String, DtValue>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, DtValue> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                // 仅仅做为标记用，不放入map
                DtValue parent = new DtValue(DtSource.DB, table);
                DtValue dtValue = new DtValue(DtSource.DB, String.valueOf(rs.getObject(i)), parent);
                rowData.put(md.getColumnName(i), dtValue);
            }
            list.add(rowData);
        }
        return list;
    }
}
