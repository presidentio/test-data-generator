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
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.model.Output;
import com.presidentio.testdatagenerator.model.Provider;
import com.presidentio.testdatagenerator.provider.DefaultValueProviderFactory;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;
import org.junit.Assert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExtendProvidersTest extends AbstractSqlTest {

    private Long testProviderValue = 123L;

    @Override
    protected List<String> getSchemaResource() {
        return Arrays.asList("extend-providers-test-schema.json");
    }

    @Override
    protected Generator buildGenerator() {
        Generator generator = new Generator();
        ValueProviderFactory valueProviderFactory = DefaultValueProviderFactory.defaultProvider().extend(new ValueProviderFactory() {
            @Override
            public ValueProvider buildValueProvider(final Provider provider) {
                Assert.assertEquals("test-provider", provider.getName());
                return new ValueProvider() {
                    @Override
                    public void init(Map props) {

                    }

                    @Override
                    public Object nextValue(Context context, Field field) {
                        return Long.valueOf(provider.getProps().get("value"));
                    }
                };
            }
        });
        generator.setValueProviderFactory(valueProviderFactory);
        return generator;
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

    protected void testDbContent(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(id) FROM user");
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        int count = resultSet.getInt(1);
        Assert.assertEquals(10, count);

        preparedStatement = connection.prepareStatement("SELECT id FROM user");
        preparedStatement.execute();
        resultSet = preparedStatement.getResultSet();
        resultSet.next();
        Long value = resultSet.getLong(1);
        Assert.assertEquals(value, testProviderValue);
    }
}
