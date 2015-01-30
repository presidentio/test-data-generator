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

/**
 * Created with IntelliJ IDEA.
 * User: Olya
 * Date: 30.01.15
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSqlTest extends AbstractGeneratorTest {

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
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:test_data_generator", "sa", "");
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
