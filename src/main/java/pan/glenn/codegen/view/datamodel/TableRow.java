/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view.datamodel;

import pan.glenn.codegen.view.config.TableColumnConfig;
import pan.glenn.codegen.view.config.TableConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class TableRow {

    private List<TableColumn> columns;
    private Map<String, TableColumn> colMap = new HashMap<String, TableColumn>();

    public TableRow(TableConfig config) {
        init(config);
    }

    private void init(TableConfig config) {
        columns = new ArrayList<TableColumn>(config.getColSize());
        List<TableColumnConfig> colConfigs = config.getColumnConfigs();
        TableColumn col;
        String colName;
        for (TableColumnConfig colCfg : colConfigs) {
            colName = colCfg.getColumnName();
            col = new TableColumn(colCfg.isEditable(), colName);
            columns.add(col);
            colMap.put(colName, col);
        }
    }

    /**
     * 获取某列的值
     * @param col
     * @return
     */
    public Object getColumnValue(int col) {
        TableColumn column = getColumn(col);
        if (column == null) {
            throw new IllegalArgumentException("列不存在");
        }
        return column.getValue();
    }

    /**
     * 获取某列的值
     * @param colName
     * @return
     */
    public Object getColumnValue(String colName) {
        TableColumn col = getColumn(colName);
        if (col == null) {
            return null;
            //throw new IllegalArgumentException("列不存在");
        }
        return col.getValue();
    }

    /**
     * 设置某列的值
     * @param col
     * @param value
     */
    public void setColumnValue(int col, Object value) {
        TableColumn column = getColumn(col);
        if (column == null) {
            return;
        }
        column.setValue(value);
    }

    /**
     * 设置某列的值
     * @param colName
     * @param value
     */
    public void setColumnValue(String colName, Object value) {
        TableColumn col = colMap.get(colName);
        if (col == null) {
            return;
        }
        col.setValue(value);
    }

    public void updateColumn(String colName, TableColumn newColumn) {
        TableColumn oldCol = getColumn(colName);
        if (oldCol == null) {
            return;
        }
        oldCol.setEditable(newColumn.isEditable());
        oldCol.setValue(newColumn.getValue());
        // 列名不变
    }

    /**
     * 获取列，指定列索引超出行列数跨度时抛出异常
     * @param col
     * @return
     */
    public TableColumn getColumn(int col) {
        if (col >= columns.size()) {
            return null;
//            throw new IllegalArgumentException("列索引超出范围：最大列数 -> " + columns.size() +
//                    "给定列索引 ->" +  col);
        }
        return columns.get(col);
    }

    // 根据列名获取列
    public TableColumn getColumn(String colName) {
        TableColumn col = colMap.get(colName);
        if (col == null) {
            return null;
            //throw new IllegalArgumentException("指定的列不存在 col -> " + colName);
        }
        return colMap.get(colName);
    }
}
