package br.com.ceosites.cachedproperties;

import com.google.common.base.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDatabaseConfiguration extends DatabaseConfiguration {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomDatabaseConfiguration.class);
    private final String table;
    private final String nameColumn;
    private final String keyColumn;
    private final String valueColumn;
    private String name;

    public CustomDatabaseConfiguration(DataSource datasource, String table, String nameColumn, String keyColumn, String valueColumn, String name) {
        super(datasource, table, nameColumn, keyColumn, valueColumn, name);
        this.table = table;
        this.nameColumn = nameColumn;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.name = name;
    }

    @Override
    public Optional<String> getProperty(String key) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet cursor = null;
        String value = null;
        try {
            conn = getDatasource().getConnection();
            pstmt = getQueryStatement(conn, getQuerySelectOne(), key);
            cursor = pstmt.executeQuery();
            while (cursor.next()) {
                value = cursor.getString(valueColumn);
            }
        } catch (SQLException e) {
            getLogger().error("An error occurred on get key " + key, e);
        } finally {
            close(conn, pstmt, cursor);
        }
        return Optional.fromNullable(value);
    }

    public Map<String, Optional<String>> getPropertiesMap() {
        Map<String, Optional<String>> results = new HashMap<String, Optional<String>>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet cursor = null;
        try {
            conn = getDatasource().getConnection();
            pstmt = getQueryStatement(conn, getQuerySelectAll(), null);
            cursor = pstmt.executeQuery();
            while (cursor.next()) {
                String key = cursor.getString(keyColumn);
                String value = cursor.getString(valueColumn);
                results.put(key, Optional.fromNullable(value));
            }
        } catch (SQLException e) {
            getLogger().error("An error occurred on get the map of keys", e);
        } finally {
            close(conn, pstmt, cursor);
        }
        return results;
    }
    
    private PreparedStatement getQueryStatement (Connection conn, String sqlQuery, String keyName) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
        if (StringUtils.isBlank(keyName) && StringUtils.isNotBlank(nameColumn)) {
            pstmt.setString(1, name);
        } 
        
        if (StringUtils.isNotBlank(keyName) && StringUtils.isNotBlank(nameColumn)) {
            pstmt.setString(1, keyName);
            pstmt.setString(2, name);
        }
        LOGGER.debug("Performing database query: {}",pstmt.toString());
        return pstmt;
    }

    private String getQuerySelectAll() {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(table);
        if (nameColumn != null) {
            query.append(" WHERE ").append(nameColumn).append("=?");
        }
        return query.toString();
    }
    
    private String getQuerySelectOne() {
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(table).append(" WHERE ").append(keyColumn).append("=?");
        if (nameColumn != null) {
            query.append(" AND ").append(nameColumn).append("=?");
        }
        return query.toString();
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occurred on closing the result set", e);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the statement", e);
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            getLogger().error("An error occured on closing the connection", e);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
