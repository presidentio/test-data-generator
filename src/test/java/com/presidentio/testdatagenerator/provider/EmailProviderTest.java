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

import java.util.HashMap;
import java.util.Map;

public class EmailProviderTest {

    @Test
    public void testNextValueWithSpecifiedDomain() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propDomain = "test.domain";
        props.put(PropConst.DOMAIN, propDomain);
        EmailProvider emailProvider = new EmailProvider();
        emailProvider.init(props);
        Object result = emailProvider.nextValue(new Context(), new Field(null, TypeConst.STRING, null));
        Assert.assertTrue(result.toString().matches(".+@\\w+\\.\\w+"));
        Assert.assertEquals(propDomain, result.toString().split("@")[1]);
    }

    @Test
    public void testNextValueWithDefaultDomain() throws Exception {
        Map<String, String> props = new HashMap<>();
        EmailProvider emailProvider = new EmailProvider();
        emailProvider.init(props);
        Object result = emailProvider.nextValue(new Context(), new Field(null, TypeConst.STRING, null));
        Assert.assertTrue(result.toString().matches(".+@\\w+\\.\\w+"));
    }
}