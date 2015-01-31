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

import com.presidentio.testdatagenerator.cons.TypeConst;

public class TypeConverter {

    public static <T> T convert(String value, String type) {
        switch (type) {
            case TypeConst.STRING:
                return (T) value;
            case TypeConst.LONG:
                return (T) Long.valueOf(value);
            case TypeConst.INT:
                return (T) Integer.valueOf(value);
            case TypeConst.BOOLEAN:
                return (T) Boolean.valueOf(value);
            default:
                throw new IllegalArgumentException("Type not known: " + type);
        }
    }

}
