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
package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.ValueProviderNameConst;
import com.presidentio.testdatagenerator.model.Provider;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefaultValueProviderFactory implements ValueProviderFactory {

    private static String NAMES;

    @Override
    public ValueProvider buildValueProvider(Provider provider) {
        Map<String, String> props = new HashMap<>();
        if (provider.getProps() != null) {
            props = new HashMap<>(provider.getProps());
        }
        switch (provider.getName()) {
            case ValueProviderNameConst.CONST:
                return new ConstValueProvider(props);
            case ValueProviderNameConst.EMAIL:
                return new EmailProvider(props);
            case ValueProviderNameConst.EXPR:
                return new ExpressionProvider(props);
            case ValueProviderNameConst.RANDOM:
                return new RandomProvider(props);
            case ValueProviderNameConst.SELECT:
                return new SelectProvider(props);
            case ValueProviderNameConst.PEOPLE_NAME:
                props.put(PropConst.ITEMS, getNames());
                props.put(PropConst.DELIMITER, "\n");
                return new SelectProvider(props);
            default:
                return null;
        }
    }

    private static String getNames() {
        if (NAMES == null) {
            try {
                NAMES = IOUtils.toString(DefaultValueProviderFactory.class.getClassLoader().getResourceAsStream("name.dat"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return NAMES;
    }

    public static CompositeValueProviderFactory defaultProvider() {
        return new CompositeValueProviderFactory().extend(new DefaultValueProviderFactory());
    }

}
