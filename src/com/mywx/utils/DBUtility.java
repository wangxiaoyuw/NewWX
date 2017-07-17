package com.mywx.utils;



import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by wangzy on 2017/6/5.连接jdbc
 */
public class DBUtility {

    //微信使用数据库
    private static BasicDataSource dataSource = null;

    public DBUtility(){

    }

    public static void init(){
        Properties dbProps = new Properties();
        try{
            dbProps.load(DBUtility.class.getClassLoader().getResourceAsStream("db.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    try{
        String driveClassName = dbProps.getProperty("jdbc.driverClassName");
        String url = dbProps.getProperty("jdbc.url");
        String username = dbProps.getProperty("jdbc.username");
        String password = dbProps.getProperty("jdbc.password");

        String initialSize = dbProps.getProperty("dataSource.initialSize");
        String minIdle = dbProps.getProperty("dataSource.minIdle");
        String maxIdle = dbProps.getProperty("dataSource.maxIdle");
        String maxWait = dbProps.getProperty("dataSource.maxWait");
        String maxActive = dbProps.getProperty("dataSource.maxActive");

        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driveClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // 初始化连接数
        if (initialSize != null)
            dataSource.setInitialSize(Integer.parseInt(initialSize));

        // 最小空闲连接
        if (minIdle != null)
            dataSource.setMinIdle(Integer.parseInt(minIdle));

        // 最大空闲连接
        if (maxIdle != null)
            dataSource.setMaxIdle(Integer.parseInt(maxIdle));

        // 超时回收时间(以毫秒为单位)
        if (maxWait != null)
            dataSource.setMaxWait(Long.parseLong(maxWait));

        // 最大连接数
        if (maxActive != null) {
            if (!maxActive.trim().equals("0"))
                dataSource.setMaxActive(Integer.parseInt(maxActive));
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("创建连接池失败!请检查设置!!!");
    }
    }

    /**
     * 数据库连接
     *
     * @return
     * @throws SQLException
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (dataSource == null) {
            init();
        }
        Connection conn = null;
        if (dataSource != null) {
            conn = dataSource.getConnection();
        }
        return conn;
    }

    /**
     * 关闭数据库
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("关闭资源失败");
                e.printStackTrace();
            }
        }
    }
}