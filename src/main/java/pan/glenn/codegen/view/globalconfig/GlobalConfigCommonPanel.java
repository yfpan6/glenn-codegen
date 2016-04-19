/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view.globalconfig;

import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.view.FormItem;
import pan.glenn.codegen.view.VFlowLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class GlobalConfigCommonPanel extends JPanel {

    private List<FormItem> formItemList;

    public GlobalConfigCommonPanel(List<FormItem> formItemList) {
        this.formItemList = formItemList;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel dbConfig = new JPanel();
        dbConfig.setLayout(new VFlowLayout());
        dbConfig.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.GRAY, 1), "数据库配置"));
        add(dbConfig);

        FormItem item = new FormItem("db.vendor" ,"数据库类型", FormItem.COMBOBOX, new String[]{"MySQL", "Oracle"});
        String config = GlobalConfig.getString("db.vendor");
        if (config == null || "".equals(config.trim())
                || !"oracle".equalsIgnoreCase(config)) {
            item.setValue("MySQL");
        } else {
            item.setValue("Oracle");
        }
        FormItem item2 = new FormItem("jdbc.driverClass", "DriverClass", FormItem.TEXT, GlobalConfig.getString("jdbc.driverClass"));
        FormItem item3 = new FormItem("jdbc.url",         "JdbcUrl       ", FormItem.TEXT, GlobalConfig.getString("jdbc.url"));
        FormItem item4 = new FormItem("jdbc.username",    "用户名        ", FormItem.TEXT, GlobalConfig.getString("jdbc.username"));
        FormItem item5 = new FormItem("jdbc.password",    "密码             ", FormItem.TEXT, GlobalConfig.getString("jdbc.password"));
        FormItem item6 = new FormItem("db.name",    "数据库名     ", FormItem.TEXT, GlobalConfig.getString("db.name"));
        dbConfig.add(item);
        dbConfig.add(item2);
        dbConfig.add(item3);
        dbConfig.add(item4);
        dbConfig.add(item5);
        dbConfig.add(item6);
        formItemList.add(item);
        formItemList.add(item2);
        formItemList.add(item3);
        formItemList.add(item4);
        formItemList.add(item5);
        formItemList.add(item6);

        add(Box.createVerticalStrut(20));

        JPanel otherConfig = new JPanel();
        otherConfig.setLayout(new VFlowLayout());
        otherConfig.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.GRAY, 1), "其他配置"));
        add(otherConfig);
        FormItem item7 = new FormItem("src.path",      "生成文件保存位置", FormItem.TEXT, GlobalConfig.getString("src.path"));

        otherConfig.add(item7);
        formItemList.add(item7);
    }

}
