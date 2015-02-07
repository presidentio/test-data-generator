/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Output;
import org.junit.After;
import org.junit.Before;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractSqlTest extends AbstractGeneratorTest {

    private static int dbIndex = 1;

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = prepareConnection();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Override
    protected void testResult(Output output) {
        try {
            executeSqlFile(output.getProps().get(PropConst.FILE));
            testDbContent(connection);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeSqlFile(String file) throws IOException {
        try {
            SqlScriptRunner sqlScriptRunner = new SqlScriptRunner(connection, true, true);
            sqlScriptRunner.runScript(new FileReader(file));
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private Connection prepareConnection() throws SQLException, IOException {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:test_data_generator" + (dbIndex++), "sa", "");
        SqlScriptRunner sqlScriptRunner = new SqlScriptRunner(conn, true, true);
        sqlScriptRunner.runScript(new InputStreamReader(SqlGeneratorTest.class.getClassLoader()
                .getResourceAsStream(getDbSchemaResource())));
        return conn;
    }

    protected void testDbContent(Connection connection) throws SQLException {

    }

    protected String getDbSchemaResource(){
        return "test-db-schema.sql";
    }

}
