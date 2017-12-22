package com.simone.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Created by AlsdGo
 */
public class C3P0Utils {

    // 获得c3p0连接池对象
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    // 获得数据库连接对象
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 获得c3p0连接池对象
    public static DataSource getDataSource() {
        return dataSource;
    }

}
