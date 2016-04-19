/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view.datamodel;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class TableColumn {

    private boolean editable;

    private String name;

    private Object value;

    public TableColumn(String name) {
        this.name = name;
    }

    public TableColumn(String name, Object value) {
        this(false, name, value);
    }

    public TableColumn(boolean editable, String name, Object value) {
        this.editable = editable;
        this.name = name;
        this.value = value;
    }

    public TableColumn(boolean editable, String name) {
        this.editable = editable;
        this.name = name;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
