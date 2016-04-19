package pan.glenn.codegen.view;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class FormItem extends JPanel {

	private static final long serialVersionUID = 3898310293254947312L;
	
	public static final int TEXT = 1;
	
	public static final int COMBOBOX = 2;
	
	public static final int CHECKBOX = 3;
	
	public static final int RADIO = 4;
	
	private static final int LABEL_MAX_WIDTH = 120;
	
	private int itemType = TEXT;
	
	private String name;
	
	
	public Component comp;

	public FormItem(String name) {
		this(name, "", TEXT, null);
	}
	
	public FormItem(String name, String label, int itemType) {
		this(name, label, itemType, null);
	}
	

	public FormItem(String name, String label, int itemType, Object defaultVal) {
		this(name, label, itemType, defaultVal, 240);
	}
	
	public FormItem(String name, String label, int itemType, Object defaultVal, int compWidth) {
		this.itemType = itemType;
		this.name = name;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Box.createHorizontalStrut(20));
		switch (itemType) {
			case COMBOBOX :
				if (defaultVal != null) {
					comp = new JComboBox((Object[]) defaultVal);
				} else {
					comp = new JComboBox(new Vector<Object>());
				}
				break;
			case CHECKBOX :
				if (defaultVal != null) {
					comp = new JCheckBox("", (Boolean) defaultVal);
				} else {
					comp = new JCheckBox();
				}
				break;
			case 4 :
				break;
			default:
				comp = new JTextField((String) defaultVal, 40);
				break;
		}
		if (label != null && label.length() > 0) {
			JLabel lab = new JLabel(label);
			lab.setBounds(0, 0, LABEL_MAX_WIDTH, 30);
			comp.setBounds(LABEL_MAX_WIDTH + 10, 0, compWidth, 30);
			this.add(lab);
			add(Box.createHorizontalStrut(20));
		}
		add(comp);
		add(Box.createHorizontalStrut(20));
	}

	public String getName() {
		return name;
	}
	
	public Object getValue() {
		Object val = null;
		switch (itemType) {
			case COMBOBOX :
				JComboBox cb = (JComboBox) comp;
				return cb.getSelectedItem();
			case CHECKBOX :
				JCheckBox ckb = (JCheckBox) comp;
				return ckb.isSelected();
			case 4 :
				break;
			default:
				JTextField tf = (JTextField) comp; 
				return tf.getText();
		}
		return val;
	}
	
	public void setValue(Object val) {
		switch (itemType) {
			case COMBOBOX :
				JComboBox cb = (JComboBox) comp;
				cb.setSelectedItem(val);
				break;
			case CHECKBOX :
				JCheckBox ckb = (JCheckBox) comp;
				ckb.setSelected((Boolean) val);
				break;
			case 4 :
				break;
			default:
				JTextField tf = (JTextField) comp; 
				tf.setText((String) val);
				break;
		}
	}
}
