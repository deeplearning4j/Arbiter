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


import org.nd4j.linalg.api.ndarray.INDArray;
import org.arbiter.nn.conf.NeuralNetConfiguration;

import java.io.Serializable;

/**
 * Interface for a layer of a neural network.
 * This has an activation function, an input and output size,
 * weights, and a bias
 *
 * @author Adam Gibson
 */
public interface Layer extends Serializable,Cloneable,Model {


       /**
     * Parameter averaging
     * @param layer the layer to merge
     * @param batchSize the batch size to merge on
     */
    void merge(Layer layer,int batchSize);


    INDArray activationMean();

    NeuralNetConfiguration conf();
    void setConfiguration(NeuralNetConfiguration conf);

    INDArray getInput();

    void setInput(INDArray input);


    INDArray preOutput(INDArray x);

    /**
     * Trigger an activation with the last specified input
     * @return the activation of the last specified input
     */
    INDArray activate();

    /**
     * Initialize the layer with the given input
     * and return the activation for this layer
     * given this input
     * @param input the input to use
     * @return
     */
    INDArray activate(INDArray input);

    /**
     * Return a transposed copy of the weights/bias
     * (this means reverse the number of inputs and outputs on the weights)
     *
     * @return the transposed layer
     */
    Layer transpose();

    /**
     * Clone the layer
     * @return
     */
    Layer clone();


    /**
     * Propagate errors backwards for a particular layer
     * @param errors the errors to propagate
     */
    void backWard(INDArray errors);



}
