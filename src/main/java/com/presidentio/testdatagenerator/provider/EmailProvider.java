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
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EmailProvider implements ValueProvider<String> {

    private RandomProvider randomProvider;

    private String domain = "email.com";

    public EmailProvider(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        if (propsCopy.containsKey(PropConst.DOMAIN)) {
            domain = propsCopy.remove(PropConst.DOMAIN);
        }
        if (!propsCopy.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + propsCopy);
        }
        randomProvider = new RandomProvider(Collections.<String, String>emptyMap());
    }

    @Override
    public String nextValue(Context context, Field field) {
        if (!field.getType().equals(TypeConst.STRING)) {
            throw new IllegalArgumentException("Illegal type for email required " + TypeConst.STRING + ": "
                    + field.getType());
        }
        return randomProvider.nextValue(context, field).toString() + "@" + domain;
    }

}
