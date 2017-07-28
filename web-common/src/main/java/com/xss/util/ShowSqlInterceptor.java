package com.xss.util;

import com.alibaba.druid.sql.SQLUtils;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StatementInterceptor;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/20 0020
 * 打印 MariaDB 的执行语句(基于 mysql 驱动的拦截器)
 */
public class ShowSqlInterceptor implements StatementInterceptor {
    @Override
    public void init(Connection connection, Properties properties) throws SQLException {

    }

    @Override
    public ResultSetInternalMethods preProcess(String s, Statement statement, Connection connection) throws SQLException {
        if(U.isBlank(s) && statement != null){
            s = statement.toString();
            if(U.isNotBlank(s) && s.contains(":")){
                s = SQLUtils.formatMySql(s.substring(s.indexOf(":")+1).trim());
            }
        }
        if(U.isNotBlank(s)){/*任何时候都打印*/
            /*只有debugger模式打印*/
            /*if(LogUtil.SQL_LOG.isDebugEnabled()){

            }*/
            LogUtil.ROOT_LOG.info(s);
        }
        return null;
    }

    @Override
    public ResultSetInternalMethods postProcess(String s, Statement statement, ResultSetInternalMethods resultSetInternalMethods, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }

    @Override
    public void destroy() {

    }
}
