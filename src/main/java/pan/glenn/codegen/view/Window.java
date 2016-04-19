package pan.glenn.codegen.view;

import pan.glenn.codegen.view.domainconfig.DomainConfigPanel;
import pan.glenn.codegen.view.globalconfig.GlobalConfigPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window {

	public static final String GLOBAL_CFG_PANEL_ID = "configPanel";
	public static final String DOMAIN_CFG_PANEL_ID = "domainConfigPanel";

	private JFrame frame = null;

	private DomainConfigPanel domainConfigPanel;
	private GlobalConfigPanel globalConfigPanel;
	
	public Window() {
		frame = new JFrame("MyBatis3代码生成");
		init();
	}
	
	public void init() {

		addCenterCardPanel();
		
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		
		int width = 1080;
		int height = 670;
		int x = 0;
		int y = 0;
		if (screensize.width < 1080) {
			width = 860;
			height = 600;
		} else {
			x = (screensize.width - 1080) / 2;
			y = (screensize.height - 670) / 2 - 25;
			if (y < 0) {
				y = 0;
			}
		}
		frame.setBounds(x, y, width, height);

		frame.addWindowListener(new WindowAdapter()
		{

			public void windowClosing(WindowEvent e)
			{
				Object[] options = {"确定","取消"};
				int response=JOptionPane.showOptionDialog(frame,
						"确定退出程序吗", "提示",
						JOptionPane.YES_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
				if (response == 0) {
					System.exit(0);
				}
			}
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	
	public void addCenterCardPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new CardLayout());

		domainConfigPanel = new DomainConfigPanel(contentPanel);
		globalConfigPanel = new GlobalConfigPanel(contentPanel, domainConfigPanel);

		frame.add(contentPanel, BorderLayout.CENTER);
	}
}
