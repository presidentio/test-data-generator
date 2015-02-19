package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
public abstract class AbstractJdbcSink extends AbstractBufferedSink {

    private Connection connection;

    @Override
    public void init(Map<String, String> props) {
        String connectionUrl = props.get(PropConst.CONNECTION_URL);
        if (connectionUrl == null) {
            throw new IllegalArgumentException("Connection url does not specified");
        }
        String jdbcDriver = props.get(PropConst.JDBC_DRIVER);
        if (jdbcDriver == null) {
            throw new IllegalArgumentException("Jdbc driver does not specified");
        }
        try {
            connection = prepareConnection(connectionUrl, jdbcDriver);
        } catch (SQLException | IOException e) {
            throw new IllegalStateException("");
        }
    }

    @Override
    public void write(String request) {
        try {
            connection.prepareStatement(request).execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to execute query: " + request, e);
        }
    }

    @Override
    public void close() {
        super.close();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection prepareConnection(String connectionUrl, String jdbcDriver) throws SQLException, IOException {
        if (jdbcDriver != null) {
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return DriverManager.getConnection(connectionUrl);
    }

}
