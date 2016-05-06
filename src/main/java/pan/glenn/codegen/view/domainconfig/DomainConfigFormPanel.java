/*
 * Copyright 2016-2017 the original author or authors.
 */
package pan.glenn.codegen.view.domainconfig;

import org.mybatis.generator.internal.util.JavaBeansUtil;
import pan.glenn.codegen.context.DomainConfig;
import pan.glenn.codegen.db.TableHandler;
import pan.glenn.codegen.util.StringUtil;
import pan.glenn.codegen.view.datamodel.TableColumnTableModel;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by PanYunFeng on 2016/4/16.
 */
public class DomainConfigFormPanel extends JPanel {

    /**
     * 数据库表
     */
    private JComboBox tableSelector;
    /**
     * 模块名称
     */
    private JTextField modelName;
    /**
     * 表别名
     */
    private JTextField alias;
    /**
     * 实体名称
     */
    private JTextField entityClassENName;
    /**
     * 实体中文名称
     */
    private JTextField entityClassCNName;
    /**
     * 页面位置
     */
    private JTextField functionUrl;
    private JTextField jspPath;
    private JTextField keyColumnName;

    private JCheckBox hasAutoInIdCol;

    private TableHandler th;

    private DomainConfigPanel configPanel;

    public DomainConfigFormPanel(DomainConfigPanel configPanel) {
        this.configPanel = configPanel;
        init();
    }

    private void init() {
        th = new TableHandler();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        this.add(Box.createVerticalStrut(3));
        this.add(row1);
        this.add(Box.createVerticalStrut(3));
        this.add(row2);
        this.add(Box.createVerticalStrut(3));

        // label
        row1.add(Box.createHorizontalStrut(5));
        JLabel label = new JLabel("表");
        row1.add(label);
        row1.add(Box.createHorizontalStrut(5));
        // 表名列表
        tableSelector =  new JComboBox(th.getTableNameList().toArray());
        tableSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String tableName = (String) tableSelector.getSelectedItem();
                    if (tableName != null && !"".equals(tableName.trim()) &&
                            tableName.indexOf("选张表吧") < 0) {
                        String classENName = JavaBeansUtil.getCamelCaseString(tableName, true);
                        entityClassENName.setText(classENName);
                        alias.setText(StringUtil.getTableAlias(tableName));
                        functionUrl.setText(JavaBeansUtil.getCamelCaseString(tableName, false) + "/list");
                        jspPath.setText(JavaBeansUtil.getCamelCaseString(tableName, false));
                        ((TableColumnTableModel) configPanel.columnTable.getModel())
                                .setData(th.getTableColumns4TCModel(
                                        tableName));
                    }
                }
            }
        });
        row1.add(tableSelector);
        row1.add(Box.createHorizontalStrut(5));

        JLabel modelNameLabel = new JLabel("模块");
        row1.add(modelNameLabel);
        row1.add(Box.createHorizontalStrut(5));
        // 表别名
        modelName = new JTextField(30);
        modelName.setToolTipText("当前表所属的功能模块，注释使用。");
        row1.add(modelName);
        row1.add(Box.createHorizontalStrut(5));

        JLabel aLabel = new JLabel("表别名");
        row1.add(aLabel);
        row1.add(Box.createHorizontalStrut(5));
        // 表别名
        alias = new JTextField(10);
        alias.setToolTipText("请为表提供一个别名，这样能减少Mapper中的代码长度");
        row1.add(alias);

        JLabel entityLabel = new JLabel("实体类名");
        row1.add(entityLabel);
        row1.add(Box.createHorizontalStrut(5));
        // 实体类名
        entityClassENName = new JTextField(50);
        entityClassENName.setToolTipText("数据对对应Entity的类名");
        row1.add(entityClassENName);

        JLabel entityCNNameLabel = new JLabel("实体名");
        row1.add(entityCNNameLabel);
        row1.add(Box.createHorizontalStrut(5));
        // 实体名
        entityClassCNName = new JTextField(20);
        entityClassCNName.setToolTipText("实体的中文名称，用于UI界面及注释");
        row1.add(entityClassCNName);
        row1.add(Box.createHorizontalStrut(5));


        row2.add(Box.createHorizontalStrut(5));
        JLabel rootUrlLabel = new JLabel("主键列");
        row2.add(rootUrlLabel);
        row2.add(Box.createHorizontalStrut(5));
        // rootUrl
        keyColumnName = new JTextField(25);
        keyColumnName.setToolTipText("表中业务主键列列名");
        row2.add(keyColumnName);
        row2.add(Box.createHorizontalStrut(5));

        JLabel jsPathLabel = new JLabel("功能入口Url");
        row2.add(jsPathLabel);
        row2.add(Box.createHorizontalStrut(5));
        // 表别名
        functionUrl = new JTextField(30);
        functionUrl.setToolTipText("Controller类中列表页的Url，无需正斜杠‘/’开头。");
        row2.add(functionUrl);
        row2.add(Box.createHorizontalStrut(5));
        JLabel jspLabel = new JLabel("Jsp页面路径");
        row2.add(jspLabel);
        row2.add(Box.createHorizontalStrut(5));
        // 表js路径
        jspPath = new JTextField(30);
        jspPath.setToolTipText("SpringMVC中jsp页面的路径，无需正斜杠‘/’开头。");
        row2.add(jspPath);
//        JLabel ll = new JLabel("指定jsp文件在view后位置，不许要.jsp后缀");
//        row2.add(ll);
        row2.add(Box.createHorizontalStrut(5));
        hasAutoInIdCol = new JCheckBox("生成文件中包含列[auto_inc_id]");
        hasAutoInIdCol.setSelected(true);
        hasAutoInIdCol.setToolTipText("选中则将在生成的实体、mapper.xml中包含auto_inc_id, 否则忽略此列");
//        String includeAutoIncId = GlobalConfig.getString("include.col.auto_inc_id");
//        if (!StringUtil.isBLank(includeAutoIncId) && "true".equals(includeAutoIncId.trim())) {
//            hasAutoInIdCol.setSelected(true);
//        }
        row2.add(hasAutoInIdCol);
        row2.add(Box.createHorizontalStrut(5));
    }

    public void updateSelectorData() {
        Object[] items = th.getTableNameList().toArray();
        DefaultComboBoxModel model = (DefaultComboBoxModel) tableSelector.getModel();

        model.removeAllElements();
        for (Object item : items) {
            model.addElement(item);
        }

    }

    public DomainConfig getDomainConfig() {
        String tableNameText = (String) tableSelector.getSelectedItem();
        String keyColumnNameText = keyColumnName.getText();
        String functionUrlText = functionUrl.getText();
        String aliasText = alias.getText();
        String entityClassCNNameText = entityClassCNName.getText();
        String entityClassENNameText = entityClassENName.getText();
        String modelNameText = modelName.getText();
        String listPageName = jspPath.getText();

        DomainConfig config = new DomainConfig();
        config.setTableName(tableNameText);
        if (StringUtil.isBLank(keyColumnNameText)) {
            config.setKeyColumnName("id");
        } else {
            config.setKeyColumnName(keyColumnNameText);
        }
        if (StringUtil.isBLank(entityClassENNameText)) {
            config.setEntityClassENName(JavaBeansUtil.getCamelCaseString(tableNameText, true));
        } else {
            config.setEntityClassENName(entityClassENNameText);
        }
        if (StringUtil.isBLank(functionUrlText)) {
            config.setFunctionUrl("/" + JavaBeansUtil.getCamelCaseString(tableNameText, false));
        } else {
            config.setFunctionUrl(functionUrlText);
        }
        if (StringUtil.isBLank(aliasText)) {
            config.setAlias(StringUtil.getTableAlias(tableNameText));
        } else {
            config.setAlias(aliasText);
        }
        if (StringUtil.isBLank(entityClassCNNameText)) {
            config.setEntityClassCNName(entityClassENNameText);
        } else {
            config.setEntityClassCNName(entityClassCNNameText);
        }
        if (StringUtil.isBLank(listPageName)) {
            config.setMainPage(JavaBeansUtil.getCamelCaseString(tableNameText, false));
        } else {
            config.setMainPage(listPageName);
        }
        config.setHasAutoInIdCol(hasAutoInIdCol.isSelected());
        config.setModelName(modelNameText);
        return config;
    }
}
