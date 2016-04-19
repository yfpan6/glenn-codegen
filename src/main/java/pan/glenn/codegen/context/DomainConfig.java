/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.context;

import org.mybatis.generator.internal.util.JavaBeansUtil;
import pan.glenn.codegen.util.StringUtil;
import pan.glenn.codegen.view.Result;

import java.util.List;

/**
 * 领域配置信息
 *
 * Created by PanYunFeng on 2016/4/16.
 */
public class DomainConfig {

    private String modelName;

    private String catalog;

    private String schema;

    /**
     * 领域表
     */
    private String tableName;

    /**
     * 别名
     */
    private String alias;

    /**
     * 主键列名
     */
    private String keyColumnName;

    /**
     * 实体中文名称
     */
    private String entityClassCNName;

    /**
     * 所属模块

    /**
     * 实体类名
     */
    private String entityClassENName;

    /**
     * 功能列表页入口Url
     */
    private String functionUrl;

    private String mainPage;

    private boolean hasAutoInIdCol;

    private List<DomainField> fields;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeyColumnName() {
        return keyColumnName;
    }

    public void setKeyColumnName(String keyColumnName) {
        this.keyColumnName = keyColumnName;
    }

    public String getEntityClassCNName() {
        return entityClassCNName;
    }

    public void setEntityClassCNName(String entityClassCNName) {
        this.entityClassCNName = entityClassCNName;
    }

    public String getEntityClassENName() {
        return entityClassENName;
    }

    public void setEntityClassENName(String entityClassENName) {
        this.entityClassENName = entityClassENName;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public boolean isHasAutoInIdCol() {
        return hasAutoInIdCol;
    }

    public void setHasAutoInIdCol(boolean hasAutoInIdCol) {
        this.hasAutoInIdCol = hasAutoInIdCol;
    }

    public List<DomainField> getFields() {
        return fields;
    }

    public void setFields(List<DomainField> fields) {
        this.fields = fields;
    }

    public Result validate() {
        if (StringUtil.isBLank(this.getFunctionUrl())) {
            return Result.fail("请填写功能入口Url");
        }
        if (fields == null || fields.size() == 0) {
            return Result.fail("空表。。。无法生成数据。");
        }
        return Result.success();
    }

    public String getFullyQualifiedTableText() {
        StringBuilder sb = new StringBuilder();

        if (catalog != null && catalog.length() > 0) {
            sb.append(catalog);
            sb.append(".");
        }

        if (schema != null && schema.length() > 0) {
            sb.append(schema);
            sb.append(".");
        } else {
            if (sb.length() > 0) {
                sb.append(".");
            }
        }

        sb.append(tableName);

        return sb.toString();
    }

    public String getCamelClassName() {
        return StringUtil.initialsToLowerCase(entityClassENName);
    }

    public String getCamelKeyColName() {
        return JavaBeansUtil.getCamelCaseString(keyColumnName, false);
    }

    public String getUpperCamelKeyColName() {
        return JavaBeansUtil.getCamelCaseString(keyColumnName, true);
    }

    public static final void main(String[] args) {
        System.out.println("com.test.hello.word".replaceAll("\\.", "/"));
    }
}
