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

package org.arbiter.nn.api;

import org.arbiter.nn.conf.NeuralNetConfiguration;

/**
 *
 * Common interface for creating neural network layers.
 *
 *
 * @author Adam Gibson
 */
public interface LayerFactory {


    /**
     * Return the layer class name
     * @return the layer class name
     */
    String layerClazzName();
    /**
     *
     * Create a layer based on the based in configuration
     * and an added context.
     * @param conf the configuration to create the layer based on
     * @param index the index of the layer
     * @param numLayers the number of total layers in the net work
     * @return the created layer
     */
    <E extends Layer> E create(NeuralNetConfiguration conf,int index,int numLayers);

    /**
     *
     * Create a layer based on the based in configuration
     * @param conf the configuration to create the layer based on
     * @return the created layer
     */
    <E extends Layer> E create(NeuralNetConfiguration conf);


    /**
     * Get the param initializer used for initializing layers
     * @return the param initializer
     */
    ParamInitializer initializer();


}
