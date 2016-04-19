/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.db;

import org.apache.commons.dbutils.DbUtils;
import pan.glenn.codegen.context.GlobalConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by PanYunFeng on 2016/4/15.
 */
public class DbUtil {

    public static final Connection getConn() {
        DbUtils.loadDriver(GlobalConfig.getString("jdbc.driverClass"));
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    GlobalConfig.getString("jdbc.url"),
                    GlobalConfig.getString("jdbc.username"),
                    GlobalConfig.getString("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
