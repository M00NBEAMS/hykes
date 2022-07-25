package com.hykes.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class C3P0Utils {
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    // 返回数据源
    public static DataSource getDataSource() {
        return dataSource;
    }

    // 得到连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            // 抛出运行时异常，可以规避编译期的报错
            throw new RuntimeException(e);
        }
    }

    // 增删改操作
    public static int update(String sql, Object... params){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 1.创建连接
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            if(params != null && params.length > 0){
                for (int i = 0; i < params.length; i++) {
                    // 设置参数
                    stmt.setObject(i + 1, params[i]);
                }
            }
            int row = stmt.executeUpdate();
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            release(null, stmt, conn);
        }
    }
    public static int update(Connection conn,String sql, Object... params){
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            if(params != null && params.length > 0){
                for (int i = 0; i < params.length; i++) {
                    // 设置参数
                    stmt.setObject(i + 1, params[i]);
                }
            }
            int row = stmt.executeUpdate();
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static void release(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // 为了提高内存的使用率，将数据库操作对象置为null，这样做便于JVM 回收空间
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

}
