package pan.glenn.codegen.view.globalconfig;

import pan.glenn.codegen.view.FormItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GlobalConfigNavPanel extends JPanel{


	private static final long serialVersionUID = -4048732910716408709L;

	private final JPanel cardPanel;

	private CardLayout card;

	private JList menus;

	public GlobalConfigNavPanel(JPanel cardPanel) {
		init();
		this.cardPanel = cardPanel;
		card = new CardLayout();
		cardPanel.setLayout(card);
	}

	private void init() {
		setLayout(new BorderLayout());
		menus = new JList(new NavListModel());
		menus.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					card.show(cardPanel, ((NavItem) menus.getSelectedValue()).code);
				}
			}
		});
		this.add(menus);
	}

	public void addNav(String navText, String panelName, JPanel panel) {
		((NavListModel)menus.getModel()).addNavItem(new NavItem(panelName, navText));
		menus.updateUI();
		cardPanel.add(panel, panelName);
	}

	class NavListModel extends AbstractListModel {

		List<NavItem> items = new ArrayList<NavItem>();

		public int getSize() {
			return items.size();
		}

		public Object getElementAt(int index) {
			return items.get(index);
		}

		public void addNavItem(NavItem navItem) {
			if (navItem == null) {
				throw new IllegalArgumentException("navItem is null");
			}
			for (NavItem item : items) {
				if (item.code.equals(navItem.code)) {
					item.text = navItem.text;
					return;
				}
			}
			items.add(navItem);
		}

		public void addAllNavItem(List<NavItem> navItems) {
			if (navItems == null) {
				throw new IllegalArgumentException("navItems is null");
			}
			for (NavItem item: navItems) {
				if (item == null) {
					continue;
				}
				addNavItem(item);
			}
		}

	}

	class NavItem {
		String code;
		String text;

		public NavItem(String code, String text) {
			this.code = code;
			this.text = text;
		}

		public String toString() {
			return "    " + text;
		}
	}

}
