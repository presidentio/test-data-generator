package com.presidentio.testdatagenerator;

import org.junit.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllGeneratorTest extends AbstractSqlFileTest {

    @Override
    protected String getSchemaResource() {
        return "test1-schema.json";
    }

    @Override
    protected void testDbContent(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(id) FROM user");
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        int count = resultSet.getInt(1);
        Assert.assertEquals(11, count);

        preparedStatement = connection.prepareStatement("SELECT count(id) FROM training");
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        resultSet.next();
        count = resultSet.getInt(1);
        Assert.assertEquals(55, count);

        preparedStatement = connection.prepareStatement("SELECT count(id) FROM exercise");
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        resultSet.next();
        count = resultSet.getInt(1);
        Assert.assertEquals(275, count);
    }
}
