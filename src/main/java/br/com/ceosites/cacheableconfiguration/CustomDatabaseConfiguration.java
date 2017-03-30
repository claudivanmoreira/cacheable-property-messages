package br.com.ceosites.cacheableconfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class CustomDatabaseConfiguration extends DatabaseConfiguration {
	
	private String table;
	private String nameColumn;
	private String keyColumn;
	private String valueColumn;
	private String name;

	public CustomDatabaseConfiguration(DataSource datasource, String table, String nameColumn, String keyColumn, String valueColumn, String name) {
		super(datasource, table, nameColumn, keyColumn, valueColumn, name);
		this.table = table;
		this.nameColumn = nameColumn;
		this.keyColumn = keyColumn;
		this.valueColumn = valueColumn;
		this.name = name;
	}
	
	public Map<String, String> getMapKeys() {
		Map<String, String> results = new HashMap<String, String>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet cursor = null;
        try {
            conn = getDatasource().getConnection();
            pstmt = conn.prepareStatement(getSelectQuery());
            if (nameColumn != null) {
                pstmt.setString(1, name);
            }
            cursor = pstmt.executeQuery();
           
            while (cursor.next()) {
            	String key = cursor.getString(keyColumn);
                Object value = cursor.getObject(valueColumn);
                results.put(key, ObjectUtils.defaultIfNull(value, StringUtils.EMPTY).toString());
            }
        } catch (SQLException e) {
        	getLogger().error("An error occurred on get the map of keys", e);
        }
        finally {
            close(conn, pstmt, cursor);
        }

        return results;
	}
	
	private String getSelectQuery() {
		 StringBuilder query = new StringBuilder("SELECT * FROM ");
	        query.append(table);
	        if (nameColumn != null) {
	            query.append(" WHERE ").append(nameColumn + "=?");
	        }
	        return query.toString();
	}
	
	
	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		}
		catch (SQLException e) {
			getLogger().error("An error occurred on closing the result set", e);
		}

		try {
			if (stmt != null) {
				stmt.close();
			}
		}
		catch (SQLException e) {
			getLogger().error("An error occured on closing the statement", e);
		}

		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch (SQLException e) {
			getLogger().error("An error occured on closing the connection", e);
		}
	}

}
