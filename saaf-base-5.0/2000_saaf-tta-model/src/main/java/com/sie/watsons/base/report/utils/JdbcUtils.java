package com.sie.watsons.base.report.utils;

import com.sie.watsons.base.report.model.inter.server.TtaFreeGoodsPolistServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc操作数据库
 */
@Component
public class JdbcUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtils.class);
    private static JdbcTemplate jdbcTemplate;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Autowired
    public JdbcUtils(JdbcTemplate jdbcTemplate){
        System.out.println("注入jdbcTemplate对象:" + jdbcTemplate);
        JdbcUtils.jdbcTemplate = jdbcTemplate;
    }

    //删除当前本地线程局部变量值
    public static void removeThreadLocalVal(){
        threadLocal.remove();
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            return jdbcTemplate.getDataSource().getConnection();
        }
        return conn;
    }

    public static void beginTransaction() throws SQLException{
        Connection conn = threadLocal.get();
        if (null != conn) {
            throw new SQLException("事务已经开启，在没有结束当前事务时，不能再开启事务！");
        }
        conn = jdbcTemplate.getDataSource().getConnection();
        threadLocal.set(conn);
        conn.setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException{
        Connection conn = threadLocal.get();
        if (conn == null) {
            throw new SQLException("当前没有事务，所以不能提交事务！");
        }
        conn.commit();
        //conn.close();
        //threadLocal.remove();
    }

    public static void rollbackTransaction() throws SQLException{
        Connection conn  = threadLocal.get();
        if (conn == null) {
            throw new SQLException("当前没有事务，所以不能回滚事务！");
        }
        conn.rollback();
        //conn.close();
        //threadLocal.remove();
    }

    public static void closeConnection() throws SQLException{
        Connection conn = threadLocal.get();
        if(conn != null){
            conn.close();
        }
        threadLocal.remove();
    }

    public static void  release(Statement st, ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;

        }
        if(st != null){
            try{
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
