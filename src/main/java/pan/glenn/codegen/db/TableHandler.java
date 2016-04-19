package pan.glenn.codegen.db;

import org.apache.commons.dbutils.DbUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import pan.glenn.codegen.context.GlobalConfig;
import pan.glenn.codegen.context.UITableConfig;
import pan.glenn.codegen.generator.util.JdbcTypeTranslator;
import pan.glenn.codegen.view.datamodel.TableRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableHandler {
	
	public static final String DEFAULT_SELECTED = "--- 选张表吧 ---";
	
	public static final String tablesSql = "select " +
			"TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT " +
			"from information_schema.TABLES where TABLE_SCHEMA = ?";

	public static final String columnsSql = "select " +
			"TABLE_CATALOG, TABLE_SCHEMA, IS_NULLABLE, NUMERIC_SCALE, NUMERIC_PRECISION," +
			"COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, COLUMN_COMMENT " +
			"from information_schema.COLUMNS  "
			+ "where TABLE_SCHEMA = ?  and TABLE_NAME = ?";
	
	public List<String> getTableNameList() {
		List<String> nameList = new ArrayList<String>();
		nameList.add(DEFAULT_SELECTED);
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			stm = conn.prepareStatement(tablesSql);
			stm.setString(1, GlobalConfig.getString("db.name"));
			rs = stm.executeQuery();
			while (rs.next()) {
				nameList.add(rs.getString("TABLE_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, stm, rs);
		}
		return nameList;
	}
	
	public List<TableRow> getTableColumns4TCModel(String tableName) {
		List<TableRow> rowList = new ArrayList<TableRow>();
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			stm = conn.prepareStatement(columnsSql);
			stm.setString(1, GlobalConfig.getString("db.name"));
			stm.setString(2, tableName);
			rs = stm.executeQuery();
			TableRow row;
			while (rs.next()) {
				String colName = rs.getString("COLUMN_NAME");
				row = new TableRow(UITableConfig.GLOAGL_TB_CONFIG);
				row.getColumn("columnName").setValue(colName);
				row.getColumn("fieldName").setValue(JavaBeansUtil.getCamelCaseString(colName, false));
				row.getColumn("columnType").setValue(rs.getString("DATA_TYPE"));
				row.getColumn("columnLength").setValue(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
				row.getColumn("fieldCNName").setValue(rs.getString("COLUMN_COMMENT"));
				row.getColumn("fieldComment").setValue(rs.getString("COLUMN_COMMENT"));
				row.getColumn("fieldJavaType").setValue(JdbcTypeTranslator
						.getJavaType(rs.getString("DATA_TYPE")).toString());
				if (rs.getString("IS_NULLABLE") != null &&
						"YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"))) {
					row.setColumnValue("isRequired", false);
				} else {
					row.setColumnValue("isRequired", true);
				}
				if (rs.getString("NUMERIC_SCALE") != null) {
					row.getColumn("columnLength").setValue(rs.getString("NUMERIC_PRECISION"));
				}
				if (isBaseField(rs.getString("COLUMN_NAME"))) {
					row.setColumnValue("isShowInEntity", false);
					row.setColumnValue("isShowInList", false);
					row.setColumnValue("isShowInAddForm", false);
					row.setColumnValue("isShowInUpdateForm", false);
				} else {
					row.setColumnValue("isShowInEntity", true);
					row.setColumnValue("isShowInList", true);
					row.setColumnValue("isShowInAddForm", true);
					row.setColumnValue("isShowInUpdateForm", true);
				}
				rowList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, stm, rs);
		}
		return rowList;
	}

	private boolean isBaseField(String columnName) {
		if (columnName == null) {
			return false;
		}
		String baseFileds = GlobalConfig.getString("base.fields");
		if (baseFileds == null || baseFileds.trim().length() == 0) {
			return false;
		}
		if (!baseFileds.startsWith("*")) {
			baseFileds = "*" + baseFileds;
		}
		if (!baseFileds.endsWith("*")) {
			baseFileds = baseFileds + "*";
		}
		GlobalConfig.setProp("base.fields", baseFileds);
		baseFileds = baseFileds.toLowerCase();
		if (baseFileds.indexOf("*" + columnName.toLowerCase() + "*") != -1) {
			return true;
		}
		return false;
	}
}
