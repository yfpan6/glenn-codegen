package pan.glenn.codegen.view.datamodel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import pan.glenn.codegen.view.config.TableConfig;

public class TableColumnTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -585921906599233673L;

	private List<TableRow> data = null;

	private TableConfig tableConfig;

	public TableColumnTableModel(TableConfig tableConfig) {
		this(tableConfig, 10);
	}

	public TableColumnTableModel(TableConfig tableConfig, int rowCount) {
		this(tableConfig, new ArrayList<TableRow>(rowCount));
	}
	
	public TableColumnTableModel(TableConfig tableConfig, List<TableRow> data) {
		this.data = data;
		this.tableConfig = tableConfig;
	}

	public void addRow(TableRow row) {
		data.add(row);
	}

	public void removeRow(int row) {
		data.remove(row);
	}

	public void clear() {
		data = new ArrayList<TableRow>();
	}

	/**
	 * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0
				|| columnIndex == 1
				|| columnIndex == 2) {
			return false;
		}
		return true;
	}

	/**
	 * 使修改的内容生效
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		data.get(row).setColumnValue(col, value);
		this.fireTableCellUpdated(row, col);
	}

	@Override
	public String getColumnName(int col) {
		return tableConfig.getHeaders()[col];
	}

	@Override
	public int getColumnCount() {
		return tableConfig.getColSize();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row).getColumnValue(col);
	}

	/**
	 * 返回数据类型
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		Object value = getValueAt(0, col);
		if (value == null) {
			return String.class;
		}
		return value.getClass();
	}

	public List<TableRow> getData() {
		return data;
	}
	
	public void setData(List<TableRow> data) {
		this.data = data;
		fireTableDataChanged();
	}

}
