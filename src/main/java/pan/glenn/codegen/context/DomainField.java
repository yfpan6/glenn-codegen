/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.context;

import org.mybatis.generator.internal.types.JdbcTypeNameTranslator;
import pan.glenn.codegen.generator.util.JavaBeanUtil;
import pan.glenn.codegen.generator.util.JdbcTypeTranslator;

/**
 * 领域中的字段
 *
 * Created by PanYunFeng on 2016/4/16.
 */
public class DomainField {
    // 字段对应的表列名
    private String columnName;
    // 字段对应的表列类型
    private String columnType;
    // 列宽
    private String columnLength;
    // 列和类字段的映射名称，默认列名转驼峰;
    private String fieldName;
    // 字段中文名，用于表单、列表字段名称显示，默认列备注或列名称
    private String fieldCNName;
    // 字段注释，默认fieldCNName;
    private String fieldComment;
    // 字段columnType对应的javaType，不配置则采用默认规则。
    private String fieldJavaType;
    // 字段长度校验， 默认列宽
    private String fieldMaxLength;
    // 是否必选，会加入判断 是否为空，
    private boolean isRequired;
    // ui模型 text, checkbox, dropdown(目前功能不强)
    private String uiElement;
    // 是否出现在实体类中
    private boolean isShowInEntity;
    // 是否出现在页面列表中
    private boolean isShowInList;
    // 是否出现在添加雷彪中；
    private boolean isShowInAddForm;
    // 是否出现在修改列表中；
    private boolean isShowInUpdateForm;

    private DomainConfig domainConfig;

    public DomainField(DomainConfig domainConfig) {
        this.domainConfig = domainConfig;
    }

    public DomainConfig getDomainConfig() {
        return domainConfig;
    }

    public String getColumnName() {
        if (columnName == null) {
            return null;
        }
        return columnName.toLowerCase();
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCNName() {
        return fieldCNName;
    }

    public void setFieldCNName(String fieldCNName) {
        this.fieldCNName = fieldCNName;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getFieldJavaType() {
        return fieldJavaType;
    }

    public void setFieldJavaType(String fieldJavaType) {
        this.fieldJavaType = fieldJavaType;
    }

    public String getFieldMaxLength() {
        return fieldMaxLength;
    }

    public void setFieldMaxLength(String fieldMaxLength) {
        this.fieldMaxLength = fieldMaxLength;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getUiElement() {
        return uiElement;
    }

    public void setUiElement(String uiElement) {
        this.uiElement = uiElement;
    }

    public boolean isShowInEntity() {
        return isShowInEntity;
    }

    public void setShowInEntity(boolean showInEntity) {
        isShowInEntity = showInEntity;
    }

    public boolean isShowInList() {
        return isShowInList;
    }

    public void setShowInList(boolean showInList) {
        isShowInList = showInList;
    }

    public boolean isShowInAddForm() {
        return isShowInAddForm;
    }

    public void setShowInAddForm(boolean showInAddForm) {
        isShowInAddForm = showInAddForm;
    }

    public boolean isShowInUpdateForm() {
        return isShowInUpdateForm;
    }

    public void setShowInUpdateForm(boolean showInUpdateForm) {
        isShowInUpdateForm = showInUpdateForm;
    }

    public boolean isStringColumn() {
        return false;
    }

    public String getJdbcType() {
        int jdbcType = JdbcTypeTranslator.getJdbcType(this.getColumnType());
        return JdbcTypeNameTranslator.getJdbcTypeName(jdbcType);
    }
}
