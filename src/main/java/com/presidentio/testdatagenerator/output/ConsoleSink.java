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
package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.model.Template;

import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class ConsoleSink implements Sink {

    @Override
    public void init(Map<String, String> props) {
        if (!props.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + props);
        }
    }

    @Override
    public void process(Template template, Map<String, Object> map) {
        System.out.println(map);
    }

    @Override
    public void close() {

    }
}
