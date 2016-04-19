/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view.config;

import java.util.List;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class TableConfig {

    private List<TableColumnConfig> columnConfigs;
    private String[] headers;

    public TableConfig(List<TableColumnConfig> columnConfigs) {
        this.columnConfigs = columnConfigs;
    }

    public int getColSize() {
        if (isEmptyTable()) {
            return 0;
        }
        return columnConfigs.size();
    }

    public String[] getHeaders() {
        if (isEmptyTable()) {
            return new String[0];
        }

        if (headers != null && headers.length > 0) {
            return headers;
        }

        headers = new String[columnConfigs.size()];
        int i = 0;
        for (TableColumnConfig pair : columnConfigs) {
            headers[i++] = pair.displayText;
        }
        return headers;
    }

    /**
     * 如果没有数据列，这位空表配置
     * @return
     */
    public boolean isEmptyTable() {
        if (columnConfigs == null || columnConfigs.size() == 0) {
            return true;
        }
        return false;
    }

    public List<TableColumnConfig> getColumnConfigs() {
        return columnConfigs;
    }

    public void setColumnConfigs(List<TableColumnConfig> columnConfigs) {
        this.columnConfigs = columnConfigs;
    }
}
