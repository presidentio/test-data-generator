/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.ValueProviderNameConst;
import com.presidentio.testdatagenerator.model.Provider;

import java.util.HashMap;
import java.util.Map;

public class DefaultValueProviderFactory implements ValueProviderFactory {

    @Override
    public ValueProvider buildValueProvider(Provider provider) {
        Map<String, String> props = new HashMap<>();
        if (provider.getProps() != null) {
            props = new HashMap<>(provider.getProps());
        }
        ValueProvider valueProvider;
        switch (provider.getName()) {
            case ValueProviderNameConst.CONST:
                valueProvider = new ConstValueProvider();
                break;
            case ValueProviderNameConst.EMAIL:
                valueProvider = new EmailProvider();
                break;
            case ValueProviderNameConst.EXPR:
                valueProvider = new ExpressionProxyProvider();
                break;
            case ValueProviderNameConst.RANDOM:
                valueProvider = new RandomProvider();
                break;
            case ValueProviderNameConst.SELECT:
                valueProvider = new SelectProvider();
                break;
            case ValueProviderNameConst.PEOPLE_NAME:
                valueProvider = new PeopleNameProvider();
                break;
            case ValueProviderNameConst.COUNTRY:
                valueProvider = new CountryProvider();
                break;
            case ValueProviderNameConst.COUNTER:
                valueProvider = new CounterProvider();
                break;
            case ValueProviderNameConst.PARENT:
                valueProvider = new ParentProvider();
                break;
            case ValueProviderNameConst.TIME:
                valueProvider = new TimeProvider();
                break;
            default:
                return null;
        }
        valueProvider.init(props);
        return valueProvider;
    }

    public static CompositeValueProviderFactory defaultProvider() {
        return new CompositeValueProviderFactory().extend(new DefaultValueProviderFactory());
    }

}
