/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceosites.cachedproperties.cache.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Claudivan Moreira
 */
public class ScriptRunner {

    private final BasicDataSource dataSource;

    public ScriptRunner(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execute(BufferedReader bufferedReader) throws IOException, SQLException {
        StringBuilder scriptBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            scriptBuilder.append(bufferedReader.readLine());
        }
        Connection con = this.dataSource.getConnection();
        con.prepareStatement(scriptBuilder.toString()).executeUpdate();
        con.close();
    }
}
