package com.simone.util;
import com.simone.bean.CustomerBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dllo on 17/12/19.
 */
public class MyQueryRunner extends QueryRunner {


    //用于添加数据
    @Override
    public int update(String sql, Object... param) throws SQLException {
        Connection connection=null;
        try {
            connection = JDBCUtil.getConnection();
            return super.update(connection,sql,param);
        }
        finally {
            JDBCUtil.release(connection);
        }
    }


    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        Connection connection=null;
        try {
            connection = JDBCUtil.getConnection();
            return super.query(connection,sql,rsh);//
        }
        finally {
            JDBCUtil.release(connection);
        }
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {

        Connection connection=null;
        try {
            connection = JDBCUtil.getConnection();
            return super.query(connection,sql, rsh, params);//
        }
        finally {
            JDBCUtil.release(connection);
        }
    }

    @Override
    public int update(String sql, Object param) throws SQLException {

        Connection connection=null;
        try {
            connection = JDBCUtil.getConnection();
            return super.update(connection,sql, param);

        }
        finally {
            JDBCUtil.release(connection);
        }
    }
}
