/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.context;

import pan.glenn.codegen.view.config.TableColumnConfig;
import pan.glenn.codegen.view.config.TableConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class UITableConfig {

    public static final TableConfig GLOAGL_TB_CONFIG;

    static {
        List<TableColumnConfig> columnConfigs = new ArrayList<TableColumnConfig>();
        // fieldMaxLength uiElement
        columnConfigs.add(new TableColumnConfig("columnName", "列名", false));
        columnConfigs.add(new TableColumnConfig("columnType", "数据类型", false));
        columnConfigs.add(new TableColumnConfig("columnLength", "长度", false));
        columnConfigs.add(new TableColumnConfig("fieldName", "类属性名", true));
        columnConfigs.add(new TableColumnConfig("fieldCNName", "中文名", true));
        columnConfigs.add(new TableColumnConfig("fieldComment", "注释", true));
        columnConfigs.add(new TableColumnConfig("fieldJavaType", "Java类型", true));
        columnConfigs.add(new TableColumnConfig("isRequired", "必选", true));
        columnConfigs.add(new TableColumnConfig("isShowInEntity", "实体类属性", true));
        columnConfigs.add(new TableColumnConfig("isShowInList", "列表（界面）", true));
        columnConfigs.add(new TableColumnConfig("isShowInAddForm", "Add表单属性", true));
        columnConfigs.add(new TableColumnConfig("isShowInUpdateForm", "Update表单属性", true));
        GLOAGL_TB_CONFIG = new TableConfig(columnConfigs);
    }
}
