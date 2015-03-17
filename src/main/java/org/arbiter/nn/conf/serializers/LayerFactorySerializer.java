/*
 * Copyright 2015 Skymind,Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.arbiter.nn.conf.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.deeplearning4j.nn.api.LayerFactory;

/**
 * Writes a field of:
 * layerFactory: layer factory class name, value.layerClazzName()
 *
 * @author Adam Gibson
 */
public class LayerFactorySerializer extends JsonSerializer<LayerFactory> {
    @Override
    public void serialize(LayerFactory value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        String write = value.getClass().getName() + "," + value.layerClazzName();
        jgen.writeString(write);

    }
}
