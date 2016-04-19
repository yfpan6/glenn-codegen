/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view.config;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class TableColumnConfig {

    // 表头对应的列名
    protected String columnName;

    // 表头显示名称
    protected String displayText;

    // 这列是否能被编辑
    protected boolean editable;

    public TableColumnConfig(String columnName, String displayText) {
        this.columnName = columnName;
        this.displayText = displayText;
    }

    public TableColumnConfig(String columnName, String displayText,
                             boolean editable) {
        this.columnName = columnName;
        this.displayText = displayText;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
