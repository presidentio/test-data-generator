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

import com.presidentio.testdatagenerator.model.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitalii_Gergel on 2/6/2015.
 */
public class CompositeValueProviderFactory implements ValueProviderFactory {

    private List<ValueProviderFactory> valueProviderFactories = new ArrayList<>();

    private CompositeValueProviderFactory() {
    }

    @Override
    public ValueProvider buildValueProvider(Provider provider) {
        for (ValueProviderFactory valueProviderFactory : valueProviderFactories) {
            ValueProvider valueProvider = valueProviderFactory.buildValueProvider(provider);
            if (valueProvider != null) {
                return valueProvider;
            }
        }
        return null;
    }

    public CompositeValueProviderFactory extend(ValueProviderFactory valueProviderFactory) {
        valueProviderFactories.add(valueProviderFactory);
        return this;
    }

    public static CompositeValueProviderFactory defaultProvider() {
        return new CompositeValueProviderFactory().extend(new DefaultValueProviderFactory());
    }
}
