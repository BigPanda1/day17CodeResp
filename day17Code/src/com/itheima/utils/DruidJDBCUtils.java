package com.itheima.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DruidJDBCUtils {

    private static DataSource ds;

    static {
        try {
            Properties pro = new Properties();
            pro.load(DruidJDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接对象
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //获取连接池(数据源)对象
    public static DataSource getDataSource(){
        return ds;
    }

    public static void close(PreparedStatement pre,Connection conn){
        close(null,pre,conn);
    }

    public static void close(ResultSet rs, PreparedStatement pre,Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pre != null){
            try {
                pre.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
