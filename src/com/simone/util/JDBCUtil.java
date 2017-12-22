package com.simone.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dllo on 17/12/19.
 */
public class JDBCUtil {
    private static String driverClass=null;
    private static String dbUrl=null;
    private static String user=null;
    private static String password=null;
    static {
        Properties properties=new Properties();
        InputStream inputStream=JDBCUtil.class.getClassLoader()
                .getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
            driverClass=properties.getProperty("driverClass");
            dbUrl=properties.getProperty("dbUrl");
            user=properties.getProperty("user");
            password=properties.getProperty("password");
            Class.forName(driverClass);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //建立连接
    public static Connection getConnection() throws SQLException {
        Connection connection= DriverManager.getConnection(dbUrl,user,password);
        return connection;
    }
    //释放资源
    public static void release(Connection connection) throws SQLException {
        if (connection!=null){
            connection.close();
        }
    }

}
