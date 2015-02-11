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
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstValueProviderTest {

    @Test(expected = IllegalArgumentException.class)
    public void testRequiredProp() throws Exception {
        new ConstValueProvider().init(Collections.<String, String>emptyMap());
    }

    @Test
    public void testNextValue() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propValue = "123";
        props.put(PropConst.VALUE, propValue);
        ConstValueProvider constValueProvider = new ConstValueProvider();
        constValueProvider.init(props);

        Object result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.STRING, null));
        Assert.assertEquals(propValue, result);

        result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.BOOLEAN, null));
        Assert.assertEquals(Boolean.valueOf(propValue), result);

        result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.INT, null));
        Assert.assertEquals(Integer.valueOf(propValue), result);

        result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.LONG, null));
        Assert.assertEquals(Long.valueOf(propValue), result);

    }
}