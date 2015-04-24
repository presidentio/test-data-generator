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
package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

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
    public void write(Object partition, String request) {
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
