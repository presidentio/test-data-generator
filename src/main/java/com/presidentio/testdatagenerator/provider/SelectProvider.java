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
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SelectProvider implements ValueProvider<Object> {

    private String[] items;

    private String delimiter = ",";
    private Random random = new Random();

    @Override
    public void init(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        if (propsCopy.containsKey(PropConst.DELIMITER)) {
            delimiter = propsCopy.remove(PropConst.DELIMITER);
        }
        String items = propsCopy.remove(PropConst.ITEMS);
        if (items == null) {
            throw new IllegalArgumentException("Items does not specified or null");
        }
        this.items = items.split(delimiter);
        if (this.items.length <= 0) {
            throw new IllegalArgumentException("Items are empty");
        }
        if (!propsCopy.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for RandomProvider: " + propsCopy);
        }

    }

    @Override
    public Object nextValue(Context context, Field field) {
        int index = random.nextInt(items.length);
        return TypeConverter.convert(items[index], field.getType());
    }

}
