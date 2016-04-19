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
import java.util.*;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class GlobalConfigPackagePanel extends JPanel {

    private List<FormItem> formItemList;

    public GlobalConfigPackagePanel(List<FormItem> formItemList) {
        this.formItemList = formItemList;
        init();
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();
        panel.setLayout(new VFlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.GRAY, 1), "基础类配置"));

        FormItem item = new FormItem("base.service", "BaseService", FormItem.TEXT, GlobalConfig.getString("base.service"));
        FormItem item2 = new FormItem("base.mapper", "BaseMapper", FormItem.TEXT, GlobalConfig.getString("base.mapper"));
        FormItem item3 = new FormItem("base.entity", "BaseEntity   ", FormItem.TEXT, GlobalConfig.getString("base.entity"));
        FormItem item4 = new FormItem("base.pkg",    "基包                 ", FormItem.TEXT, GlobalConfig.getString("base.pkg"));
        FormItem item51 = new FormItem("controller.pkg",   "Service包名  ", FormItem.TEXT, GlobalConfig.getString("controller.pkg"));
        FormItem item5 = new FormItem("service.pkg",   "Service包名  ", FormItem.TEXT, GlobalConfig.getString("service.pkg"));
        FormItem item6 = new FormItem("entity.pkg",    "Entity包名     ", FormItem.TEXT, GlobalConfig.getString("entity.pkg"));
        FormItem item7 = new FormItem("mapper.pkg",    "Mapper包名     ", FormItem.TEXT, GlobalConfig.getString("mapper.pkg"));
        FormItem item8 = new FormItem("xml.mapper.pkg","MapperXML包名", FormItem.TEXT, GlobalConfig.getString("xml.mapper.pkg"));

        panel.add(item);
        panel.add(item2);
        panel.add(item3);
        panel.add(item4);
        panel.add(item51);
        panel.add(item5);
        panel.add(item6);
        panel.add(item7);
        panel.add(item8);
        formItemList.add(item);
        formItemList.add(item2);
        formItemList.add(item3);
        formItemList.add(item4);
        formItemList.add(item51);
        formItemList.add(item5);
        formItemList.add(item6);
        formItemList.add(item7);
        formItemList.add(item8);


        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.GRAY, 1), "生成业务类配置"));
        panel2.setLayout(new VFlowLayout());
        FormItem item9 = new FormItem("file.comment.author", "注释<作者>        ", FormItem.TEXT, GlobalConfig.getString("file.comment.author"));
        FormItem item10 = new FormItem("file.comment.project", "注释<项目名称>", FormItem.TEXT, GlobalConfig.getString("file.comment.project"));
        FormItem item11 = new FormItem("file.comment.copyright", "注释<版权信息>", FormItem.TEXT, GlobalConfig.getString("file.comment.copyright"));
        panel2.add(item9);
        panel2.add(item10);
        panel2.add(item11);
        formItemList.add(item9);
        formItemList.add(item10);
        formItemList.add(item11);
        add(panel);
        add(panel2);
    }
}
