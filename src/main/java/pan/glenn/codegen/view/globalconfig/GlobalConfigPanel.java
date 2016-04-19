package pan.glenn.codegen.view.globalconfig;

import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.view.*;
import pan.glenn.codegen.view.Window;
import pan.glenn.codegen.view.domainconfig.DomainConfigPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GlobalConfigPanel extends JPanel{


	private static final long serialVersionUID = -4048732910716408709L;

	public List<FormItem> formItemList = new ArrayList<FormItem>();

	private JPanel cardPanel;

	private DomainConfigPanel domainConfigPanel;

	public GlobalConfigPanel(JPanel cardPanel, DomainConfigPanel domainConfigPanel) {
		this.cardPanel = cardPanel;
		cardPanel.add(this, Window.GLOBAL_CFG_PANEL_ID);
		this.domainConfigPanel = domainConfigPanel;
		init();
	}

	private void init() {
		final JPanel cardPanel1=new JPanel();
		GlobalConfigNavPanel navPanel = new GlobalConfigNavPanel(cardPanel1);
		navPanel.addNav("常规", "dbConfig", new GlobalConfigCommonPanel(formItemList));
		navPanel.addNav("类配置", "packageConfig", new GlobalConfigPackagePanel(formItemList));
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				false, navPanel, cardPanel1);
	    /*
	     * 设置splitPane1的分隔线位置，0.3是相对于splitPane1的大小而定，因此这个值的范围在0.0～1.0
	     * 中。若你使用整数值来设置splitPane的分隔线位置，如第34行所示，则所定义的值以pixel为计算单位
	    */
		splitPane1.setDividerLocation(200);
		splitPane1.setResizeWeight(0.3);

		//设置JSplitPane是否可以展开或收起(如同文件总管一般)，设为true表示打开此功能。
		splitPane1.setOneTouchExpandable(false);
		splitPane1.setDividerSize(15);// 设置分隔线宽度的大小，以pixel为计算单位。

		setLayout(new BorderLayout());
		add(splitPane1, BorderLayout.CENTER);

		JToolBar bar = new JToolBar();
		JButton settingBtn = new JButton("Back >>");
		settingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redoGlobalConfig();
				domainConfigPanel.updateTables();
				((CardLayout) cardPanel.getLayout()).show(cardPanel, Window.DOMAIN_CFG_PANEL_ID);
			}
		});
		bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));
		bar.setEnabled(false);
		bar.add(Box.createHorizontalGlue());
		bar.add(settingBtn);
		bar.add(Box.createHorizontalStrut(10));
		add(bar, BorderLayout.NORTH);
	}

	private void redoGlobalConfig() {
		for (FormItem formItem : formItemList) {
			GlobalConfig.setProp(formItem.getName(), (String) formItem.getValue());
		}
		GlobalConfig.save();
	}

}
