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
import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExpressionProvider implements ValueProvider {

    private String expr;
    private Serializable compiledExpression;

    @Override
    public void init(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        expr = propsCopy.remove(PropConst.EXPR);
        if (expr == null) {
            throw new IllegalArgumentException("Value does not specified or null");
        }
        if (!propsCopy.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + propsCopy);
        }
        compiledExpression = MVEL.compileExpression(expr);
    }

    @Override
    public Object nextValue(Context context, Field field) {
        Class type;
        switch (field.getType()) {
            case TypeConst.VERBATIM:
            case TypeConst.STRING:
                type = String.class;
                break;
            case TypeConst.LONG:
                type = Long.class;
                break;
            case TypeConst.INT:
                type = Integer.class;
                break;
            case TypeConst.BOOLEAN:
                type = Boolean.class;
                break;
            default:
                throw new IllegalArgumentException("Field type not known: " + field.getType());
        }
        synchronized (context.getVariables()) {
            return MVEL.executeExpression(compiledExpression, context, context.getVariables(), type);
        }
    }
}
