/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceosites.cachedproperties.cache.test.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Claudivan Moreira
 */
public class DatabaseUtils {

    static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtils.class);
    
    public static DataSource prepareDatabase() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:dataSource?hsqldb.sqllog=3");
        dataSource.setUsername("SA");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        ScriptRunner scriptRunner = new ScriptRunner(dataSource);
        
        try {
            scriptRunner.execute(new BufferedReader(new FileReader("src/main/resources/db/create-db.sql")));
            scriptRunner.execute(new BufferedReader(new FileReader("src/main/resources/db/insert-data.sql")));
        } catch (IOException ex) {
            LOGGER.error("Error on read SQL files.", ex);
        } catch (SQLException ex) {
            LOGGER.error("Error on  execute SQL script.", ex);
        }
        
        return dataSource;
    }

}
