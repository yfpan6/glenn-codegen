package pan.glenn.codegen.view.domainconfig;

import org.apache.tools.ant.types.Mapper;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import pan.glenn.codegen.context.DomainConfig;
import pan.glenn.codegen.context.DomainField;
import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.context.UITableConfig;
import pan.glenn.codegen.db.TableHandler;
import pan.glenn.codegen.freemarker.UITemplate;
import pan.glenn.codegen.freemarker.UITemplateFile;
import pan.glenn.codegen.generator.EntityGenerator;
import pan.glenn.codegen.generator.MapperGenerator;
import pan.glenn.codegen.generator.ServiceGenerator;
import pan.glenn.codegen.generator.util.JdbcTypeTranslator;
import pan.glenn.codegen.util.StringUtil;
import pan.glenn.codegen.view.*;
import pan.glenn.codegen.view.Window;
import pan.glenn.codegen.view.datamodel.TableColumnTableModel;
import pan.glenn.codegen.view.datamodel.TableRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * 数据库表选择
 * 
 * @author Taylen
 *
 */
public class DomainConfigPanel extends JPanel {
	
	private DomainConfig config = new DomainConfig();

	/**
	 * 
	 */
	private static final long serialVersionUID = 7535909354295645290L;

	private JPanel cardPanel;

	JTable columnTable;

	private DomainConfigFormPanel formPanel;

	public DomainConfigPanel(JPanel cardPanel) {
		this.cardPanel = cardPanel;
		cardPanel.add(this, pan.glenn.codegen.view.Window.DOMAIN_CFG_PANEL_ID);
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		addBtnBar();
		createTableColumnPanel();
	}

	private void addBtnBar() {
		JToolBar bar = new JToolBar();
		bar.setEnabled(false);
		bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));

		JButton settingBtn = new JButton("* Setting");
		settingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cardPanel.getLayout()).show(cardPanel, Window.GLOBAL_CFG_PANEL_ID);
			}
		});

		JButton generateBtn = new JButton("@ 生成");

		generateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DomainConfig domainConfig = getDomainConfig();
				Result result = domainConfig.validate();
				if (result.isSuccess()) {
					try {
						new EntityGenerator(domainConfig).generateAndwriteToFile();
						new ServiceGenerator(domainConfig).generateAndwriteToFile();
						new MapperGenerator(domainConfig).generateAndwriteToFile();

						String mapperPackage = GlobalConfig.getString("mapper.pkg");

						Map<String, Object> map = new HashMap<String, Object>();
						map.put("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
						map.put("author", GlobalConfig.getString("file.comment.author"));
						map.put("fcProject", GlobalConfig.getString("file.comment.project"));
						map.put("fcCopyright", GlobalConfig.getString("file.comment.copyright"));
						map.put("controllerPkg", GlobalConfig.getString("controller.pkg"));
						map.put("domainConfig",domainConfig);
						map.put("mapperPackage", mapperPackage);
						map.put("entityPackage", GlobalConfig.getString("entity.pkg"));

						String mapperDir = getPath(mapperPackage);
						String controllerDir = getPath(GlobalConfig.getString("controller.pkg"));
						UITemplate.write(map, UITemplateFile.MAPPER,
								domainConfig.getEntityClassENName() + "Mapper",
								mapperDir
						);
						UITemplate.write(map, UITemplateFile.CONTROLLER,
								domainConfig.getEntityClassENName() + "Controller",
								controllerDir
						);
						JOptionPane.showMessageDialog(null, "完成，请到" + GlobalConfig.getString("src.path")
								+ "下查看生成文件。",
								"提示信息", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					return;
				}
				JOptionPane.showMessageDialog(null, result.getMsg(),
						"错误提示", JOptionPane.ERROR_MESSAGE);
			}
		});

		bar.add(Box.createHorizontalStrut(5));
		bar.add(generateBtn);
		bar.add(Box.createHorizontalGlue());
		bar.add(settingBtn);
		bar.add(Box.createHorizontalStrut(10));
		add(bar, BorderLayout.NORTH);
	}

	private String getPath(String pkg) {
		if (!StringUtil.isBLank(pkg)) {
			pkg = pkg.replaceAll("\\.","/");
		} else {
			pkg = "";
		}
		String srcPath = GlobalConfig.getString("src.path");
		String dir = null;
		if (StringUtil.isBLank(srcPath)) {
			dir = null;
		} else {
			if (srcPath.endsWith("/") || srcPath.endsWith("\\")) {
				dir = srcPath + pkg;
			} else {
				dir = srcPath + "/" + pkg;
			}
		}
		return dir;
	}

	private void createTableColumnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		formPanel = new DomainConfigFormPanel(this);
		panel.add(formPanel, BorderLayout.NORTH);
		columnTable = new JTable(new TableColumnTableModel(UITableConfig.GLOAGL_TB_CONFIG));
		JScrollPane scroll = new JScrollPane(columnTable);
//		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(scroll);
		this.add(panel, BorderLayout.CENTER);
	}
	
	public DomainConfig getDomainConfig() {
		DomainConfig config = formPanel.getDomainConfig();
		config.setFields(getFields(config));
		return config;
	}

	public void updateTables() {
		formPanel.updateSelectorData();
	}

	private List<DomainField> getFields(DomainConfig domainConfig) {
		List<TableRow> rows = ((TableColumnTableModel) columnTable.getModel()).getData();
		List<DomainField> fields = new ArrayList<DomainField>();
		DomainField field;
		String columnName;
		for (TableRow row : rows) {
			columnName = toStringVal(row, "columnName");
			if (!domainConfig.isHasAutoInIdCol() &&
					"auto_inc_id".equalsIgnoreCase(columnName)) {
				continue;
			}
			field = new DomainField(domainConfig);
			field.setColumnName(columnName);
			field.setColumnLength(toStringVal(row, "columnLength"));
			field.setColumnType(toStringVal(row, "columnType"));
			field.setFieldCNName(toStringVal(row, "fieldCNName"));
			field.setFieldComment(toStringVal(row, "fieldComment"));
			field.setFieldJavaType(toStringVal(row, "fieldJavaType"));
			field.setFieldMaxLength(toStringVal(row, "fieldMaxLength"));
			field.setFieldName(toStringVal(row, "fieldName"));
			field.setRequired(toBooleanVal(row, "required"));
			field.setShowInEntity(toBooleanVal(row, "showInEntity"));
			field.setShowInList(toBooleanVal(row, "showInList"));
			field.setShowInAddForm(toBooleanVal(row, "showInAddForm"));
			field.setShowInUpdateForm(toBooleanVal(row, "showInUpdateForm"));
			field.setUiElement(toStringVal(row, "uiElement"));

			if (StringUtil.isBLank(field.getFieldName())) {
				field.setFieldName(JavaBeansUtil.getCamelCaseString(field.getColumnName(), false));
			}
			if (StringUtil.isBLank(field.getFieldJavaType())) {
				field.setFieldJavaType(JdbcTypeTranslator
						.getJavaType(field.getColumnType())
						.toString());
			}

			fields.add(field);
		}
		return fields;
	}

	private String toStringVal(TableRow row, String colName) {
		Object val = row.getColumnValue(colName);
		if (val == null) {
			return null;
		}
		return (String) val;
	}

	private boolean toBooleanVal(TableRow row, String colName) {
		Object val = row.getColumnValue(colName);
		if (val == null) {
			return false;
		}
		return (Boolean) val;
	}

}
