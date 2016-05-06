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

    private static final String urlParams = "?characterEncoding=utf-8&useUnicode=true";
    private static final StringBuilder sb = new StringBuilder("jdbc:mysql://");

    public static final Connection getConn() {
        DbUtils.loadDriver(GlobalConfig.getString("jdbc.driverClass"));
        Connection conn = null;
        try {
            sb.setLength(13);
            sb.append(GlobalConfig.getString("jdbc.db.host"));
            sb.append(":");
            sb.append(GlobalConfig.getString("jdbc.db.port")).append("/");
            sb.append(GlobalConfig.getString("db.name"));
            sb.append(urlParams);
            conn = DriverManager.getConnection(
                    sb.toString(),
                    GlobalConfig.getString("jdbc.username"),
                    GlobalConfig.getString("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
