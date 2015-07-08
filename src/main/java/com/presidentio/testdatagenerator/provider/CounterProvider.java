/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

import java.util.Map;

public class CounterProvider implements ValueProvider {

    public static final String INC = "++";
    public static final String DEC = "--";
    public static final String EQ = "==";

    private String var;

    private String op = "++";

    @Override
    public void init(Map<String, String> props) {
        if (props.containsKey(PropConst.OP)) {
            op = props.get(PropConst.OP);
        }
        var = props.get(PropConst.VAR);
        if (var == null) {
            throw new IllegalArgumentException("Var does not specified or null");
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        Map<String, Object> vars = context.getVariables();
        Long varVal;
        synchronized (context.getVariables()) {
            varVal = Long.valueOf(vars.get(var).toString());
            if (varVal == null) {
                varVal = 0L;
            }
            Long newVal;

            switch (op) {
                case INC:
                    newVal = varVal + 1;
                    break;
                case DEC:
                    newVal = varVal - 1;
                    break;
                case EQ:
                    newVal = varVal;
                    break;
                default:
                    throw new RuntimeException("Unknown operation: " + op);
            }
            vars.put(var, newVal);
        }
        return TypeConverter.convert(varVal.toString(), field.getType());
    }
}
