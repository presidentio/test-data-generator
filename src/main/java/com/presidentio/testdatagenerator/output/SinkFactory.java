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

import com.presidentio.testdatagenerator.cons.SinkTypeConst;
import com.presidentio.testdatagenerator.model.Output;

public class SinkFactory {

    public Sink getSink(Output output) {
        Sink sink;
        switch (output.getType()) {
            case SinkTypeConst.CONSOLE:
                sink = new ConsoleSink();
                break;
            case SinkTypeConst.FILE:
                sink = new FileSink();
                break;
            case SinkTypeConst.SQL_DIRECT:
                sink = new SqlDirectSink();
                break;
            case SinkTypeConst.ES_DIRECT:
                sink = new EsDirectSink();
                break;
            default:
                throw new IllegalArgumentException("Unknown sink type: " + output.getType());
        }
        sink.init(output.getProps());
        return sink;
    }

}
