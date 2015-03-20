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
import com.presidentio.testdatagenerator.context.Parent;
import com.presidentio.testdatagenerator.model.Field;

import java.util.Map;

public class ParentProvider implements ValueProvider {

    private int depth = 1;

    private String field;

    @Override
    public void init(Map<String, String> props) {
        if (props.containsKey(PropConst.DEPTH)) {
            depth = Integer.valueOf(props.get(PropConst.DEPTH));
        }
        field = props.get(PropConst.FIELD);
        if (field == null) {
            throw new IllegalArgumentException("Field does not specified or null");
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        Parent parent = context.getParent();
        for (int i = 1; i < depth; i++) {
            parent = parent.getParent();
        }
        return TypeConverter.convert(parent.get(this.field).toString(), field.getType());
    }
}
