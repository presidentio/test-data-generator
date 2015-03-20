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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionProxyProvider implements ValueProvider {

    private static final Pattern PARENT_EXP = Pattern.compile("(parent\\.)+(\\w+)");
    private static final Pattern COUNTER_INC = Pattern.compile("(\\w+)\\+\\+");

    private String expr;
    private ValueProvider valueProvider;

    @Override
    public void init(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        expr = propsCopy.remove(PropConst.EXPR);
        if (expr == null) {
            throw new IllegalArgumentException("Value does not specified or null");
        }
        Matcher matcher = PARENT_EXP.matcher(expr);
        if (matcher.matches()) {
            valueProvider = new ParentProvider();
            Map<String, String> params = new HashMap<>(2);
            params.put(PropConst.DEPTH, "" + countOccurrence(expr, '.'));
            params.put(PropConst.FIELD, matcher.group(2));
            valueProvider.init(params);
        } else {
            matcher = COUNTER_INC.matcher(expr);
            if (matcher.matches()) {
                Map<String, String> params = new HashMap<>(1);
                params.put(PropConst.VAR, matcher.group(1));
                valueProvider = new CounterProvider();
                valueProvider.init(params);
            } else {
                valueProvider = new ExpressionProvider();
                valueProvider.init(props);
            }
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        return valueProvider.nextValue(context, field);
    }

    private int countOccurrence(String str, char part) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == part) {
                count++;
            }
        }
        return count;
    }
}
