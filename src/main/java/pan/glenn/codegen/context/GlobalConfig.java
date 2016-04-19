/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.context;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 全局配置
 *
 * Created by PanYunFeng on 2016/4/16.
 */
public class GlobalConfig {

    private static final String appPropsFile = "app.properties";

    public static final Properties props = new Properties();

    private static final String app_prop_file_path;

    static {
//        URL path = Thread.currentThread().getContextClassLoader().getResource("app.properties");
//        input = new FileInputStream(new File(path.toURI()));
        String classpath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath();
        app_prop_file_path = classpath + "app.properties";
        FileReader input = null;
        try {
            input = new FileReader(app_prop_file_path);
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

        props.put("date.today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public static void setProp(String key, String value) {
        props.put(key, value);
    }

    public static String getString(String key) {
        if (key == null) {
            return null;
        }
        return props.getProperty(key);
    }

    public static final void save() {
        FileWriter output = null;
        try {
            output = new FileWriter(app_prop_file_path);
            props.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
